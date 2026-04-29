package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelecionarLenteActivity extends AppCompatActivity {

    private String categoriaLente = ""; // Armazena a escolha (Grau ou Multifocal)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_lente);

        // 1. Ajuste da Status Bar para não invadir a área de notificações
        View main = findViewById(R.id.main_lente);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Recuperar dados da tela anterior (Nome do óculos)
        String nomeOculos = getIntent().getStringExtra("NOME_OCULOS");
        TextView txtNome = findViewById(R.id.txtNomeProdutoLente);
        if (nomeOculos != null && txtNome != null) {
            txtNome.setText(nomeOculos);
        }

        // 3. Referências dos Cards
        CardView cardGrau = findViewById(R.id.cardGrau);
        // Se você adicionou o ID no XML para o multifocal:
        // CardView cardMultifocal = findViewById(R.id.cardMultifocal);

        // 4. Lógica de Seleção (Efeito visual de clique)
        cardGrau.setOnClickListener(v -> {
            categoriaLente = "Grau";
            // Aqui você pode mudar a cor da borda ou fundo para indicar seleção
            Toast.makeText(this, "Selecionado: Lente de Grau", Toast.LENGTH_SHORT).show();
        });

        // 5. Botão Próxima Etapa
        findViewById(R.id.btnProximaEtapa).setOnClickListener(v -> {
            if (categoriaLente.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione um tipo de lente", Toast.LENGTH_SHORT).show();
            } else {
                // AGORA ABRE A ETAPA 2
                Intent intent = new Intent(this, SelecionarEspessuraActivity.class);
                startActivity(intent);
            }
        });

        // 6. Configurar Menu Inferior
        BottomNavigationView bottomMenu = findViewById(R.id.includeBottomMenu);
        bottomMenu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_inicio) {
                // Volta para a Home e limpa a pilha de telas
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}