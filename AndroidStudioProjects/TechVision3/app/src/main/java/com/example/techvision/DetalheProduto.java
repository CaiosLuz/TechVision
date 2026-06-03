package com.example.techvision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.techvision.model.Produto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DetalheProduto extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    // Elementos da tabela de características
    private TextView txtTechMaterial, txtTechFormato, txtTechGenero, txtTechAro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        // 1. Ajuste de Insets
        View root = findViewById(R.id.drawer_layout);
        EditText etPesquisar = findViewById(R.id.etPesquisarDetalhe);

        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, 0);

                if (etPesquisar != null && etPesquisar.getParent() != null) {
                    View containerMenuSuperior = (View) etPesquisar.getParent();
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) containerMenuSuperior.getLayoutParams();
                    params.topMargin = systemBars.top;
                    containerMenuSuperior.setLayoutParams(params);
                }
                return insets;
            });
        }

        // 2. Referências dos componentes normais
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);

        ImageView imgProduto = findViewById(R.id.imgProduto);
        TextView txtNome = findViewById(R.id.txtNomeProduto);
        TextView txtPreco = findViewById(R.id.txtPrecoProduto);
        TextView txtDescricao = findViewById(R.id.txtDescricaoProduto);
        Button btnComprar = findViewById(R.id.btnComprar);
        Button btnAdicionarCarrinho = findViewById(R.id.btnAdicionarCarrinho);

        // Referências da tabela técnica inserida
        txtTechMaterial = findViewById(R.id.txtTechMaterial);
        txtTechFormato = findViewById(R.id.txtTechFormato);
        txtTechGenero = findViewById(R.id.txtTechGenero);
        txtTechAro = findViewById(R.id.txtTechAro);

        // 3. Recebendo dados vindos da Home
        Produto produto = (Produto) getIntent().getSerializableExtra("PRODUTO_SELECIONADO");
        if (produto != null) {
            Glide.with(this)
                    .load(produto.getImagemUrl())
                    .placeholder(R.mipmap.lente_normal_round)
                    .into(imgProduto);

            txtNome.setText(produto.getNome());
            txtPreco.setText(String.format("R$ %.2f", produto.getPreco()));

            if (produto.getDescricao() != null && !produto.getDescricao().isEmpty()) {
                txtDescricao.setText(produto.getDescricao());
            } else {
                txtDescricao.setText("Sem descrição disponível para este modelo.");
            }

            // Alimentação inteligente da tabela com base no modelo
            preencherTabelaEspecificacoes(produto.getNome());
        }

        // 4. Configurar Clique: Comprar Agora (Repassa a URL ou recurso da imagem também!)
        if (btnComprar != null && produto != null) {
            btnComprar.setOnClickListener(v -> {
                Intent intent = new Intent(this, SelecionarLenteActivity.class);

                // Passando os dados de forma explícita e direta
                intent.putExtra("NOME_OCULOS", produto.getNome());
                intent.putExtra("PRECO_OCULOS", produto.getPreco());

                // É aqui que a mágica acontece: passamos a mesma string que o Glide leu na Home
                intent.putExtra("IMAGEM_OCULOS", produto.getImagemUrl());

                startActivity(intent);
            });
        }

        // 5. Configurar Clique: Adicionar ao Carrinho
        if (btnAdicionarCarrinho != null) {
            btnAdicionarCarrinho.setOnClickListener(v -> {
                Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CarrinhoActivity.class));
            });
        }

        // 6. Clique na barra de pesquisa
        if (etPesquisar != null) {
            etPesquisar.setOnClickListener(v -> startActivity(new Intent(this, PesquisaActivity.class)));
        }

        // 7. Configurar Menu Inferior Padronizado
        configurarMenu(bottomMenu);

        // 8. Menu Lateral (Drawer)
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.nav_login_drawer) {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                return true;
            });
        }
    }

    /**
     * Preenche as linhas do TableLayout de acordo com palavras-chave encontradas no nome do produto.
     * Excelente solução visual para capturar dados dinâmicos em mocks e apresentações!
     */
    private void preencherTabelaEspecificacoes(String nomeProduto) {
        if (nomeProduto == null || txtTechMaterial == null) return;

        String nomeLower = nomeProduto.toLowerCase();

        if (nomeLower.contains("ray-ban") || nomeLower.contains("aviador")) {
            txtTechMaterial.setText("Metal Galvanizado");
            txtTechFormato.setText("Aviador / Clássico");
            txtTechGenero.setText("Unissex");
            txtTechAro.setText("Aro Total Fino");
        } else if (nomeLower.contains("oakley") || nomeLower.contains("sport")) {
            txtTechMaterial.setText("O Matter (Injetado)");
            txtTechFormato.setText("Esportivo / Retangular");
            txtTechGenero.setText("Masculino");
            txtTechAro.setText("Fechado Reforçado");
        } else if (nomeLower.contains("ana hickmann") || nomeLower.contains("vogue")) {
            txtTechMaterial.setText("Acetato Italiano Hipoalergênico");
            txtTechFormato.setText("Gatinho / Oval");
            txtTechGenero.setText("Feminino");
            txtTechAro.setText("Fechado");
        } else {
            // Fallback genérico elegante caso entre outro produto da API
            txtTechMaterial.setText("Acetato de Celulose");
            txtTechFormato.setText("Retangular Moderno");
            txtTechGenero.setText("Unissex");
            txtTechAro.setText("Fechado");
        }
    }

    private void configurarMenu(BottomNavigationView bottomMenu) {
        if (bottomMenu == null) return;
        bottomMenu.getMenu().setGroupCheckable(0, false, true);
        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                finish();
                return true;
            }
            if (id == R.id.nav_modelos) {
                startActivity(new Intent(this, CategoriasActivity.class));
                return true;
            }
            if (id == R.id.nav_carrinho) {
                startActivity(new Intent(this, CarrinhoActivity.class));
                return true;
            }
            if (id == R.id.nav_mais && drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            return false;
        });
    }
}