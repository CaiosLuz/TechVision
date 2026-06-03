package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class ResumoCompraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);

        // 1. Ajuste de Insets Mapeado Corretamente para a Raiz e TopBar Grossa
        View root = findViewById(R.id.main_resumo);
        View topBar = findViewById(R.id.topBarResumo);

        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, 0); // Zera paddings conflitantes

                if (topBar != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) topBar.getLayoutParams();
                    params.topMargin = insets.top; // Empurra a barra para baixo da Notificação nativa
                    topBar.setLayoutParams(params);
                }
                return windowInsets;
            });
        }

        Intent intentRecebido = getIntent();

        // Inicialização padrão caso extras falhem
        String nomeOculos = "Produto";
        double precoOculos = 0.0;
        String tipoLente = "Não selecionado";
        String espessuraLente = "Não selecionado";
        String imagemUrl = "";
        double esfOD = 0.0, cilOD = 0.0, esfOE = 0.0, cilOE = 0.0;
        int eixoOD = 0, eixoOE = 0;

        if (intentRecebido != null) {
            nomeOculos = intentRecebido.getStringExtra("NOME_OCULOS");
            precoOculos = intentRecebido.getDoubleExtra("PRECO_OCULOS", 0.0);
            tipoLente = intentRecebido.getStringExtra("TIPO_LENTE");
            espessuraLente = intentRecebido.getStringExtra("ESPESSURA");
            imagemUrl = intentRecebido.getStringExtra("IMAGEM_OCULOS");

            // Resgate dos dados da IA
            esfOD = intentRecebido.getDoubleExtra("OD_ESFERICO", 0.0);
            cilOD = intentRecebido.getDoubleExtra("OD_CILINDRICO", 0.0);
            eixoOD = intentRecebido.getIntExtra("OD_EIXO", 0);

            esfOE = intentRecebido.getDoubleExtra("OE_ESFERICO", 0.0);
            cilOE = intentRecebido.getDoubleExtra("OE_CILINDRICO", 0.0);
            eixoOE = intentRecebido.getIntExtra("OE_EIXO", 0);
        }

        // 2. Vinculando componentes da UI
        ImageView imgProdutoResumo = findViewById(R.id.imgProdutoResumo);
        TextView txtNome = findViewById(R.id.txtNomeProdutoResumo);
        TextView txtSelecoes = findViewById(R.id.txtDetalhesSelecao);
        TextView txtValor = findViewById(R.id.txtValorFinal);

        TextView tvEsfOD = findViewById(R.id.tvEsfOD);
        TextView tvCilOD = findViewById(R.id.tvCilOD);
        TextView tvEixoOD = findViewById(R.id.tvEixoOD);

        TextView tvEsfOE = findViewById(R.id.tvEsfOE);
        TextView tvCilOE = findViewById(R.id.tvCilOE);
        TextView tvEixoOE = findViewById(R.id.tvEixoOE);

        // 3. Renderizando a Foto do Óculos Ampliada
        if (imgProdutoResumo != null) {
            Glide.with(this)
                    .load(imagemUrl)
                    .placeholder(R.mipmap.lente_normal_round)
                    .error(R.drawable.oculos1) // Fallback local seguro para o print se o extra estiver nulo
                    .into(imgProdutoResumo);
        }

        // Setando os textos na tela
        if (txtNome != null) txtNome.setText(nomeOculos);

        String resumoFormatado = String.format("Tipo: %s\nEspessura: %s", tipoLente, espessuraLente);
        if (txtSelecoes != null) txtSelecoes.setText(resumoFormatado);
        if (txtValor != null) txtValor.setText(String.format(Locale.getDefault(), "R$ %.2f", precoOculos));

        // Aplicando os dados recebidos da IA nas tabelas da UI
        if (tvEsfOD != null) tvEsfOD.setText(String.format(Locale.getDefault(), "%.2f", esfOD));
        if (tvCilOD != null) tvCilOD.setText(String.format(Locale.getDefault(), "%.2f", cilOD));
        if (tvEixoOD != null) tvEixoOD.setText(String.valueOf(eixoOD));

        if (tvEsfOE != null) tvEsfOE.setText(String.format(Locale.getDefault(), "%.2f", esfOE));
        if (tvCilOE != null) tvCilOE.setText(String.format(Locale.getDefault(), "%.2f", cilOE));
        if (tvEixoOE != null) tvEixoOE.setText(String.valueOf(eixoOE));

        // Lógica do botão Concluir
        findViewById(R.id.btnConcluirCompra).setOnClickListener(v -> {
            Intent intent = new Intent(this, SucessoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Menu Inferior
        BottomNavigationView bottomMenu = findViewById(R.id.includeBottomMenu);
        if (bottomMenu != null) {
            bottomMenu.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.nav_inicio) {
                    Intent intentHome = new Intent(this, HomeActivity.class);
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentHome);
                    finish();
                    return true;
                }
                return false;
            });
        }
    }
}