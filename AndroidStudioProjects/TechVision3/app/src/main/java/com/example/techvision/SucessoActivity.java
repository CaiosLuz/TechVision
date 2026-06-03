package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SucessoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        // 1. Ajuste da Barra de Gestos e Notificações (Resolve o fundo branco)
        View main = findViewById(R.id.main_sucesso);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Mantém padding no topo (status bar), mas 0 no fundo (barra de gestos)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Lógica dos botões
        findViewById(R.id.btnVoltarHome).setOnClickListener(v -> {
            // Limpa a pilha de atividades e volta para a Home
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnVisualizarPedido).setOnClickListener(v -> {
            // Futura implementação de Meus Pedidos
        });

        // 3. Menu Inferior Padronizado
        BottomNavigationView bottomMenu = findViewById(R.id.includeBottomMenu);
        bottomMenu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_inicio) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}