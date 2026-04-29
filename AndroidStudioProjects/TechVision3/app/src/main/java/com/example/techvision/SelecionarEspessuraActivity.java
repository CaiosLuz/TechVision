package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelecionarEspessuraActivity extends AppCompatActivity {

    private String espessuraEscolhida = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_espessura);

        CardView cardNormal = findViewById(R.id.cardNormal);
        CardView cardFina = findViewById(R.id.cardFina);
        CardView cardSuperFina = findViewById(R.id.cardSuperFina);

        cardNormal.setOnClickListener(v -> selecionar("Normal"));
        cardFina.setOnClickListener(v -> selecionar("Fina"));
        cardSuperFina.setOnClickListener(v -> selecionar("Super Fina"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Aplica o recuo apenas no topo para a barra de notificações
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        findViewById(R.id.btnProximaEtapa2).setOnClickListener(v -> {
            if (espessuraEscolhida.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione uma espessura", Toast.LENGTH_SHORT).show();
            } else {
                // Navega para a tela de Anexar Receita
                Intent intent = new Intent(this, AnexarReceitaActivity.class);
                intent.putExtra("ESPESSURA", espessuraEscolhida); // Passa o dado se precisar
                startActivity(intent);
            }
        });
    }

    private void selecionar(String tipo) {
        espessuraEscolhida = tipo;
        Toast.makeText(this, tipo + " selecionada", Toast.LENGTH_SHORT).show();
    }
}