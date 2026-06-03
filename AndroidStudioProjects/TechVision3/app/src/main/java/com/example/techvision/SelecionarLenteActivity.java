package com.example.techvision;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class SelecionarLenteActivity extends AppCompatActivity {

    private String categoriaLente = "";
    private MaterialCardView cardGrau, cardMultifocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // CORREÇÃO AQUI: Use o XML correto da Seleção de Lente
        setContentView(R.layout.activity_selecionar_lente);

        // 1. Ajuste Mapeado para a barra escura de 50dp (Evita sobreposição com os ícones do sistema)
        View root = findViewById(R.id.main_lente);
        View topBar = findViewById(R.id.topBarLente);

        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, 0); // Zera paddings antigos da raiz

                if (topBar != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) topBar.getLayoutParams();
                    params.topMargin = insets.top; // Empurra a barra para baixo da Notificação nativa
                    topBar.setLayoutParams(params);
                }
                return windowInsets;
            });
        }

        // 2. Nome do Óculos
        String nomeOculos = getIntent().getStringExtra("NOME_OCULOS");
        String urlImagem = getIntent().getStringExtra("IMAGEM_OCULOS"); // Recebe a URL/URI

        TextView txtNome = findViewById(R.id.txtNomeProdutoLente);
        ImageView imgProdutoLente = findViewById(R.id.imgProdutoLente);

        if (nomeOculos != null && txtNome != null) txtNome.setText(nomeOculos);

        // CARREGAMENTO DINÂMICO COM GLIDE (Isso resolve a mudança de imagem)
        if (imgProdutoLente != null && urlImagem != null) {
            com.bumptech.glide.Glide.with(this)
                    .load(urlImagem) // Carrega a imagem enviada da Home
                    .placeholder(R.mipmap.lente_normal_round)
                    .into(imgProdutoLente);
        }

        // 3. Referências dos MaterialCardViews
        cardGrau = findViewById(R.id.cardGrau);
        cardMultifocal = findViewById(R.id.cardMultifocal);

        // 4. Lógica de Seleção com Borda
        cardGrau.setOnClickListener(v -> atualizarSelecao("Grau"));
        cardMultifocal.setOnClickListener(v -> atualizarSelecao("Multifocal"));

        // 5. Botão Próxima Etapa
        findViewById(R.id.btnProximaEtapa).setOnClickListener(v -> {
            if (categoriaLente.isEmpty()) {
                Toast.makeText(this, "Selecione um tipo de lente", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, SelecionarEspessuraActivity.class);
                // Repassa tudo de forma segura para as próximas telas
                intent.putExtra("NOME_OCULOS", getIntent().getStringExtra("NOME_OCULOS"));
                intent.putExtra("PRECO_OCULOS", getIntent().getDoubleExtra("PRECO_OCULOS", 0.0));
                intent.putExtra("IMAGEM_OCULOS", getIntent().getStringExtra("IMAGEM_OCULOS"));
                // Adiciona o novo dado
                intent.putExtra("TIPO_LENTE", categoriaLente);
                startActivity(intent);
            }
        });

        // 6. Menu Inferior
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

    // Função auxiliar para gerenciar as bordas
    private void atualizarSelecao(String escolha) {
        categoriaLente = escolha;
        int corPrimaria = Color.parseColor("#304FFE");

        if (escolha.equals("Grau")) {
            // Ativa borda no Grau
            cardGrau.setStrokeWidth(6);
            cardGrau.setStrokeColor(corPrimaria);
            // Desativa no Multifocal
            cardMultifocal.setStrokeWidth(0);
        } else {
            // Ativa borda no Multifocal
            cardMultifocal.setStrokeWidth(6);
            cardMultifocal.setStrokeColor(corPrimaria);
            // Desativa no Grau
            cardGrau.setStrokeWidth(0);
        }
    }
}