package com.example.techvision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.example.techvision.model.Produto;
import com.example.techvision.network.ProdutoService;
import com.example.techvision.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvProdutos, rvProdutosOfertas;
    private ProdutoAdapter adapter, adapterOfertas;
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Produto> listaOfertas = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Configuração de tela e Insets
        View main = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Referências e Menus
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);
        EditText etPesquisarHome = findViewById(R.id.etPesquisarHome);
        TextView tvAvatarUsuario = findViewById(R.id.tvAvatarUsuario);

        // --- ATUALIZAÇÃO DO NOME NO MENU LATERAL ---
        if (navigationView != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView tvNomeUsuario = headerView.findViewById(R.id.tvNomeUsuario);

            SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
            String nome = sp.getString("usuario_nome", "Usuário");

            if (tvNomeUsuario != null) {
                tvNomeUsuario.setText("Olá, " + nome);
            }
        }

        // 3. Configuração dos Carrosséis
        configurarRecyclers();

        // 4. Carregar Dados
        carregarProdutosDaApi();

        // 5. Pesquisa, Avatar e Menus
        if (etPesquisarHome != null) {
            etPesquisarHome.setOnClickListener(v -> startActivity(new Intent(this, PesquisaActivity.class)));
        }

        if (tvAvatarUsuario != null) {
            tvAvatarUsuario.setOnClickListener(v -> startActivity(new Intent(this, ComprasActivity.class)));
        }

        configurarMenus(bottomMenu);
    }

    private void configurarRecyclers() {
        // Carrossel 1
        rvProdutos = findViewById(R.id.rvProdutos);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new LinearSnapHelper().attachToRecyclerView(rvProdutos);
        adapter = new ProdutoAdapter(listaProdutos, this::abrirProduto);
        rvProdutos.setAdapter(adapter);

        // Carrossel 2
        rvProdutosOfertas = findViewById(R.id.rvProdutosOfertas);
        rvProdutosOfertas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new LinearSnapHelper().attachToRecyclerView(rvProdutosOfertas);
        adapterOfertas = new ProdutoAdapter(listaOfertas, this::abrirProduto);
        rvProdutosOfertas.setAdapter(adapterOfertas);
    }

    private void carregarProdutosDaApi() {
        SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
        String token = sp.getString("auth_token", null);

        // Se NÃO tem token, usamos mock automaticamente
        if (token == null || token.isEmpty()) {
            carregarDadosMockados();
            return;
        }

        // Se TEM token, tenta conectar no servidor
        ProdutoService service = RetrofitClient.getClient().create(ProdutoService.class);
        Call<List<Produto>> call = service.listarProdutos(token);

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    atualizarListas(response.body());
                } else {
                    Log.e("API_STATUS", "Erro na API, usando mock como fallback.");
                    carregarDadosMockados();
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Log.e("API_ERROR", "Falha de conexão, usando mock como fallback: " + t.getMessage());
                carregarDadosMockados();
            }
        });
    }

    private void atualizarListas(List<Produto> todosProdutos) {
        if (isFinishing() || isDestroyed()) return;

        listaProdutos.clear();
        listaOfertas.clear();

        for (int i = 0; i < todosProdutos.size(); i++) {
            if (i % 2 == 0) listaProdutos.add(todosProdutos.get(i));
            else listaOfertas.add(todosProdutos.get(i));
        }

        // Garante que a UI seja atualizada na Thread principal
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            adapterOfertas.notifyDataSetChanged();
        });
    }

    // MÉTODOS AUXILIARES ADICIONADOS AQUI DENTRO DA CLASSE
    private void carregarDadosMockados() {
        List<Produto> listaMock = new ArrayList<>();
        String uriOculos1 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos1;
        String uriOculos2 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos2;

        // Preenche com os dados de exemplo
        listaMock.add(new Produto("Ana Hickmann NINA 4", 559.90, "Armação de Grau Premium", uriOculos1));
        listaMock.add(new Produto("Ray-Ban Aviador Clássico", 680.00, "Óculos de Sol Proteção UV", uriOculos2));
        listaMock.add(new Produto("Oakley Holbrook Sport", 450.00, "Armação Esportiva Resistente", uriOculos1));
        listaMock.add(new Produto("Atitude AT11", 321.30, "Lente: Anti-Reflexo Inclusa", uriOculos2));
        listaMock.add(new Produto("Vogue Eyewear Chic", 399.90, "Armação Acetato Moderna", uriOculos1));
        listaMock.add(new Produto("Chilli Beans Casual", 289.00, "Estilo Urbano Dia a Dia", uriOculos2));

        atualizarListas(listaMock);
    }

    private void configurarMenus(BottomNavigationView bottomMenu) {
        if (bottomMenu == null) return;
        bottomMenu.setSelectedItemId(R.id.nav_inicio);
        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) return true;
            if (id == R.id.nav_carrinho) {
                startActivity(new Intent(this, CarrinhoActivity.class));
                return true;
            }
            if (id == R.id.nav_modelos) {
                startActivity(new Intent(this, CategoriasActivity.class));
                return true;
            }
            if (id == R.id.nav_mais && drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            return false;
        });

        // Ouvinte único para o NavigationView lateral
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.nav_compras_drawer) {
                    startActivity(new Intent(this, ComprasActivity.class));

                } else if (id == R.id.nav_favoritos_drawer) {
                    // Adicione a navegação de favoritos aqui quando criar a tela
                    Toast.makeText(this, "Favoritos em breve!", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.nav_login_drawer) {
                    startActivity(new Intent(this, LoginActivity.class));

                } else if (id == R.id.nav_login_drawer) {
                    // Ação de Sair: Limpa o token e o nome salvos para deslogar o usuário
                    SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
                    sp.edit().clear().apply();

                    Toast.makeText(this, "Você saiu da conta", Toast.LENGTH_SHORT).show();

                    // Opcional: Redireciona para a tela de login após deslogar
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                return true;
            });
        }
    }

    public void abrirProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProduto.class);
        intent.putExtra("PRODUTO_SELECIONADO", produto);
        startActivity(intent);
    }
}