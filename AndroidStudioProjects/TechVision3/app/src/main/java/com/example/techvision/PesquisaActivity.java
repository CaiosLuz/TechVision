package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PesquisaActivity extends AppCompatActivity {

    private RecyclerView rvResultados;
    private ProdutoAdapter adapter;
    private List<Produto> listaCompleta = new ArrayList<>();
    private List<Produto> listaFiltrada = new ArrayList<>();
    private EditText etPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        View main = findViewById(R.id.main_pesquisa);

        // Aplica o padding dinâmico para respeitar a barra de status e notificações
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Aplica o recuo apenas no topo (systemBars.top)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        etPesquisar = findViewById(R.id.etPesquisar);
        rvResultados = findViewById(R.id.rvResultados);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);

        // Seleciona o ícone de Modelos no menu inferior
        bottomMenu.setSelectedItemId(R.id.nav_modelos);

        carregarMocks();

        // Configura o Adapter (reutilizando o da Home)
        listaFiltrada.addAll(listaCompleta);
        adapter = new ProdutoAdapter(listaFiltrada, this::abrirProduto);
        rvResultados.setLayoutManager(new GridLayoutManager(this, 2));
        rvResultados.setAdapter(adapter);

        // Lógica de filtro em tempo real
        etPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrar(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        // Configuração simples de navegação para voltar à Home
        bottomMenu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_inicio) {
                finish(); // Volta para a Home
                return true;
            }
            return false;
        });
    }

    private void carregarMocks() {
        listaCompleta.add(new Produto("Ana Hickmann NINA 4", "R$ 559,90", "10% de Desconto no Pix", R.drawable.oculos1, "⭐⭐⭐⭐⭐ 4.8"));
        listaCompleta.add(new Produto("Atitude AT11", "R$ 321,30", "10% de Desconto no Pix", R.drawable.oculos1, "⭐⭐⭐⭐ 4.5"));
        listaCompleta.add(new Produto("Ray-Ban Aviator", "R$ 780,00", "5% de Desconto no Pix", R.drawable.oculos1, "⭐⭐⭐⭐⭐ 5.0"));
    }

    private void filtrar(String texto) {
        listaFiltrada.clear();
        for (Produto p : listaCompleta) {
            if (p.getNome().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void abrirProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProduto.class);
        intent.putExtra("PRODUTO_SELECIONADO", produto);
        startActivity(intent);
    }
}