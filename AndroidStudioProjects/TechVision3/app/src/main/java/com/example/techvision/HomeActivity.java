package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvProdutos;
    private List<Produto> listaMocks = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Corrigir invasão da barra de status (Executar antes de buscar as views)
        View main = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Referências
        EditText etPesquisarHome = findViewById(R.id.etPesquisarHome);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        rvProdutos = findViewById(R.id.rvProdutos);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);

        // 3. Clique na barra de pesquisa (Qualquer lugar do campo)
        if (etPesquisarHome != null) {
            etPesquisarHome.setOnClickListener(v -> {
                startActivity(new Intent(this, PesquisaActivity.class));
            });
        }

        // 4. Configurar RecyclerView
        carregarMocks();
        rvProdutos.setLayoutManager(new GridLayoutManager(this, 2));
        ProdutoAdapter adapter = new ProdutoAdapter(listaMocks, this::abrirProduto);
        rvProdutos.setAdapter(adapter);

        // 5. Menu Inferior
        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_modelos) {
                // Abre Categorias
                startActivity(new Intent(this, CategoriasActivity.class));
                return true;
            } else if (id == R.id.nav_mais) {
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            return false;
        });

        // 6. Cliques Menu Lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_login_drawer) {
                startActivity(new Intent(this, LoginActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }

    private void carregarMocks() {
        listaMocks.clear();
        listaMocks.add(new Produto("Ana Hickmann NINA 4", "R$ 559,90", "10% de Desconto no Pix", R.drawable.oculos1, "⭐⭐⭐⭐⭐ 4.8"));
        listaMocks.add(new Produto("Atitude AT11", "R$ 321,30", "10% de Desconto no Pix", R.drawable.oculos1, "⭐⭐⭐⭐ 4.5"));
    }

    public void abrirProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProduto.class);
        intent.putExtra("PRODUTO_SELECIONADO", produto);
        startActivity(intent);
    }
}