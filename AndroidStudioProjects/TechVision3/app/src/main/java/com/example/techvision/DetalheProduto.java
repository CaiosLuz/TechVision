package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetalheProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        // 1. Ajuste da Barra de Status
        View main = findViewById(R.id.main);
        if (main != null) {
            ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }

        // 2. Referências dos componentes
        ImageView imgProduto = findViewById(R.id.imgProduto);
        TextView txtNome = findViewById(R.id.txtNomeProduto); // Variável declarada como txtNome
        TextView txtPreco = findViewById(R.id.txtPrecoProduto);
        TextView txtAvaliacao = findViewById(R.id.txtAvaliacao);
        Button btnComprar = findViewById(R.id.btnComprar);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenuNav);

        // 3. Recebendo dados da Home
        Produto produto = (Produto) getIntent().getSerializableExtra("PRODUTO_SELECIONADO");
        if (produto != null) {
            imgProduto.setImageResource(produto.getImagemRes());
            txtNome.setText(produto.getNome());
            txtPreco.setText(produto.getPreco());
            txtAvaliacao.setText(produto.getAvaliacao());
        }

        // 4. Configurar clique para a tela de Seleção de Lente
        if (btnComprar != null) {
            btnComprar.setOnClickListener(v -> {
                Intent intent = new Intent(this, SelecionarLenteActivity.class);
                // CORREÇÃO: Usando a variável correta 'txtNome' que foi declarada acima
                intent.putExtra("NOME_OCULOS", txtNome.getText().toString());
                startActivity(intent);
            });
        }

        // 5. Menu Inferior
        if (bottomMenu != null) {
            bottomMenu.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_inicio) {
                    finish(); // Apenas fecha para voltar à Home
                    return true;
                } else if (id == R.id.nav_modelos) {
                    startActivity(new Intent(this, CategoriasActivity.class));
                    return true;
                }
                return false;
            });
        }
    }
}