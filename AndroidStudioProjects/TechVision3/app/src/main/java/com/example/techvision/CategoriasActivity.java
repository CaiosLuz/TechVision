package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.widget.Toast;

public class CategoriasActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        drawerLayout = findViewById(R.id.drawer_layout);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // 1. Insets
        View main = findViewById(R.id.main_categorias);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Menu Inferior Padronizado
        if (bottomMenu != null) {
            bottomMenu.setSelectedItemId(R.id.nav_modelos);
            bottomMenu.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_inicio) {
                    startActivity(new Intent(this, HomeActivity.class));
                    return true;
                }
                if (id == R.id.nav_modelos) return true;
                if (id == R.id.nav_carrinho) {
                    startActivity(new Intent(this, CarrinhoActivity.class));
                    return true;
                }
                if (id == R.id.nav_mais) {
                    drawerLayout.openDrawer(GravityCompat.END);
                    return true;
                }
                return false;
            });
        }

        // 3. Menu Lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_login_drawer) {
                startActivity(new Intent(this, LoginActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        configurarClique(R.id.catArmacoes, "Armações");
        configurarClique(R.id.catSol, "Sol");
        configurarClique(R.id.catLentes, "Lentes");
        configurarClique(R.id.catMarcas, "Marcas");
    }

    private void configurarClique(int id, String nomeCategoria) {
        View view = findViewById(id);
        if (view != null) {
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, PesquisaActivity.class);
                intent.putExtra("CATEGORIA_SELECIONADA", nomeCategoria);
                startActivity(intent);
            });
        }
    }
}