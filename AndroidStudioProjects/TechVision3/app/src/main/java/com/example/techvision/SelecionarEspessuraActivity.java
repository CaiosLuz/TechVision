package com.example.techvision;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class SelecionarEspessuraActivity extends AppCompatActivity {

    private String espessuraEscolhida = "";
    private MaterialCardView cardNormal, cardFina, cardSuperFina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_espessura);

        // 1. Ajuste Mapeado para a barra escura de 50dp (Evita invasões de layout)
        View root = findViewById(R.id.main_espessura);
        View topBar = findViewById(R.id.topBarEspessura);

        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, 0); // Zera paddings da raiz para manter a barra colada no topo

                if (topBar != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) topBar.getLayoutParams();
                    params.topMargin = insets.top; // Empurra a barra escura para baixo da barra de status do Android
                    topBar.setLayoutParams(params);
                }
                return windowInsets;
            });
        }

        // 2. Referências dos MaterialCardViews
        cardNormal = findViewById(R.id.cardNormal);
        cardFina = findViewById(R.id.cardFina);
        cardSuperFina = findViewById(R.id.cardSuperFina);

        // 3. Cliques de Seleção
        cardNormal.setOnClickListener(v -> atualizarSelecao("Normal"));
        cardFina.setOnClickListener(v -> atualizarSelecao("Fina"));
        cardSuperFina.setOnClickListener(v -> atualizarSelecao("Super Fina"));

        // 4. Botão Próxima Etapa
        findViewById(R.id.btnProximaEtapa2).setOnClickListener(v -> {
            if (espessuraEscolhida.isEmpty()) {
                Toast.makeText(this, "Selecione uma espessura", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, AnexarReceitaActivity.class);
                // Repassa tudo
                intent.putExtra("NOME_OCULOS", getIntent().getStringExtra("NOME_OCULOS"));
                intent.putExtra("PRECO_OCULOS", getIntent().getDoubleExtra("PRECO_OCULOS", 0.0));
                intent.putExtra("TIPO_LENTE", getIntent().getStringExtra("TIPO_LENTE"));
                intent.putExtra("IMAGEM_OCULOS", getIntent().getStringExtra("IMAGEM_OCULOS")); // Mantém o tráfego da URL da imagem seguro
                // Adiciona a espessura
                intent.putExtra("ESPESSURA", espessuraEscolhida);
                startActivity(intent);
            }
        });

        // 5. Menu Inferior
        BottomNavigationView bottomMenu = findViewById(R.id.includeBottomMenu);
        if (bottomMenu != null) {
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

    private void atualizarSelecao(String tipo) {
        espessuraEscolhida = tipo;
        int azulTechVision = Color.parseColor("#304FFE");

        // Resetar todas as bordas
        cardNormal.setStrokeWidth(0);
        cardFina.setStrokeWidth(0);
        cardSuperFina.setStrokeWidth(0);

        // Ativar borda no selecionado
        if (tipo.equals("Normal")) {
            cardNormal.setStrokeWidth(6);
            cardNormal.setStrokeColor(azulTechVision);
        } else if (tipo.equals("Fina")) {
            cardFina.setStrokeWidth(6);
            cardFina.setStrokeColor(azulTechVision);
        } else if (tipo.equals("Super Fina")) {
            cardSuperFina.setStrokeWidth(6);
            cardSuperFina.setStrokeColor(azulTechVision);
        }
    }
}