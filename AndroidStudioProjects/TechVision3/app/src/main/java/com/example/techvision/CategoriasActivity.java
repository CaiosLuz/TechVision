package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoriasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        // 1. Ajuste da Status Bar
        View main = findViewById(R.id.main_categorias);
        if (main != null) {
            ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }

        // 2. Menu Inferior
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);
        if (bottomMenu != null) {
            bottomMenu.setSelectedItemId(R.id.nav_modelos);
            bottomMenu.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.nav_inicio) {
                    finish();
                    return true;
                }
                return false;
            });
        }

        // 3. Cliques nos itens da lista (Protegidos)
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