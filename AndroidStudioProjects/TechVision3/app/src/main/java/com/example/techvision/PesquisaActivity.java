package com.example.techvision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techvision.model.Produto;
import com.example.techvision.network.ProdutoService;
import com.example.techvision.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisaActivity extends AppCompatActivity {

    private RecyclerView rvResultados;
    private ProdutoAdapter adapter;
    private List<Produto> listaCompleta = new ArrayList<>();
    private List<Produto> listaFiltrada = new ArrayList<>();
    private EditText etPesquisar;

    // === CHAVE DE CONTROLE: Mude para 'false' quando o backend principal for ligado! ===
    private final boolean usarModoMockParaPrints = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        View main = findViewById(R.id.main_pesquisa);

        // Aplica o padding dinâmico para respeitar a barra de status e notificações
        if (main != null) {
            ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }

        etPesquisar = findViewById(R.id.etPesquisar);
        rvResultados = findViewById(R.id.rvResultados);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);

        // Seleciona o ícone de Modelos no menu inferior
        if (bottomMenu != null) {
            bottomMenu.setSelectedItemId(R.id.nav_modelos);

            // Configuração de navegação do menu inferior
            bottomMenu.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_inicio) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    return true;
                }
                if (id == R.id.nav_carrinho) {
                    startActivity(new Intent(this, CarrinhoActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
        }

        // Configura o Adapter e o Layout Manager (Grade de 2 colunas)
        adapter = new ProdutoAdapter(listaFiltrada, this::abrirProduto);
        rvResultados.setLayoutManager(new GridLayoutManager(this, 2));
        rvResultados.setAdapter(adapter);

        // Dispara o carregamento inteligente
        carregarDadosDosProdutos();

        // Lógica de filtro em tempo real
        if (etPesquisar != null) {
            etPesquisar.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filtrar(s.toString());
                }
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void carregarDadosDosProdutos() {
        if (usarModoMockParaPrints) {
            // === MODO DE TESTE LOCAL (Imagens do drawable renderizando offline) ===
            listaCompleta.clear();

            // Mapeia o recurso local para string que o Glide consiga carregar
            String uriOculos1 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos1; // Troque pelos seus drawables de óculos reais
            String uriOculos2 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos2;

            listaCompleta.add(new Produto("Ana Hickmann NINA 4", 559.90, "10% de Desconto no Pix", uriOculos1));
            listaCompleta.add(new Produto("Atitude AT11", 321.30, "10% de Desconto no Pix", uriOculos2));
            listaCompleta.add(new Produto("Ray-Ban Aviador", 780.00, "5% de Desconto no Pix", uriOculos1));
            listaCompleta.add(new Produto("Oakley Holbrook Sport", 450.00, "Armação Esportiva Resistente", uriOculos2));

            // Exibe tudo por padrão ao entrar na tela
            listaFiltrada.clear();
            listaFiltrada.addAll(listaCompleta);
            adapter.notifyDataSetChanged();
            Log.d("P_MOCK", "Dados mockados injetados na pesquisa com sucesso!");

        } else {
            // === MODO PRODUÇÃO (Bate na API real via Retrofit) ===
            ProdutoService service = RetrofitClient.getClient().create(ProdutoService.class);
            SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
            String token = sp.getString("auth_token", "");

            Call<List<Produto>> call = service.listarProdutos(token);
            call.enqueue(new Callback<List<Produto>>() {
                @Override
                public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        listaCompleta.clear();
                        listaCompleta.addAll(response.body());

                        listaFiltrada.clear();
                        listaFiltrada.addAll(listaCompleta);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Produto>> call, Throwable t) {
                    Log.e("API_ERROR_PESQUISA", t.getMessage());
                    Toast.makeText(PesquisaActivity.this, "Erro ao buscar dados do servidor.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void filtrar(String texto) {
        listaFiltrada.clear();

        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaCompleta);
        } else {
            listaFiltrada.addAll(
                    listaCompleta.stream()
                            .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(texto.toLowerCase()))
                            .collect(Collectors.toList())
            );
        }

        adapter.notifyDataSetChanged();
    }

    public void abrirProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProduto.class);
        intent.putExtra("PRODUTO_SELECIONADO", produto);
        startActivity(intent);
    }
}