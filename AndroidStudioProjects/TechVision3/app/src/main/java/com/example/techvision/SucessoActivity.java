package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SucessoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        // Ajuste da barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Lógica para voltar à tela inicial (MainActivity ou similar)
        findViewById(R.id.btnVoltarHome).setOnClickListener(v -> {
            finish(); // Fecha esta tela
        });

        findViewById(R.id.btnVisualizarPedido).setOnClickListener(v -> {
            // Aqui você abriria uma tela de "Meus Pedidos" no futuro
        });
    }
}