package com.example.techvision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techvision.model.Produto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {

    private RecyclerView rvCarrinho;
    private CarrinhoAdapter adapter;
    private List<Produto> itensCarrinho = new ArrayList<>();
    private TextView txtTotal;
    private DrawerLayout drawerLayout;

    // === CHAVE DE CONTROLE: Mude para 'false' quando o backend principal for ligado! ===
    private final boolean usarModoMockParaPrints = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // 2. Referências (Garantidas no topo para o ajuste de Insets usar)
        rvCarrinho = findViewById(R.id.rvCarrinho);
        txtTotal = findViewById(R.id.txtTotalCarrinho);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);
        EditText etPesquisar = findViewById(R.id.etPesquisarCarrinho);
        Button btnFinalizar = findViewById(R.id.btnFinalizarCompra);

        // 1. Ajuste Dinâmico de Insets - SOLUÇÃO COMPLETA PARA O MENU SUPERIOR
        View root = findViewById(R.id.drawer_layout);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

                // Zera o padding do DrawerLayout para não esmagar a estrutura interna
                v.setPadding(0, 0, 0, 0);

                // Força o container real do Menu Superior (o pai direto do etPesquisar) a descer o tamanho exato da barra de status
                if (etPesquisar != null && etPesquisar.getParent() != null) {
                    View containerMenuSuperior = (View) etPesquisar.getParent();

                    // Altera os LayoutParams para garantir uma margem superior segura impedindo a invasão
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) containerMenuSuperior.getLayoutParams();
                    params.topMargin = systemBars.top;
                    containerMenuSuperior.setLayoutParams(params);
                }

                return insets;
            });
        }

        // 3. Configurar Lista (Adapter)
        carregarItensDoCarrinho();

        adapter = new CarrinhoAdapter(itensCarrinho, posicao -> {
            itensCarrinho.remove(posicao);
            adapter.notifyItemRemoved(posicao);
            adapter.notifyItemRangeChanged(posicao, itensCarrinho.size());
            calcularTotal();
        });
        rvCarrinho.setLayoutManager(new LinearLayoutManager(this));
        rvCarrinho.setAdapter(adapter);
        calcularTotal();

        // 4. Configurar Menu Inferior Padronizado
        configurarMenuInferior(bottomMenu);

        // 5. Configurar Menu Lateral (Drawer)
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

        // 6. Cliques e Lógica
        if (etPesquisar != null) {
            etPesquisar.setOnClickListener(v -> startActivity(new Intent(this, PesquisaActivity.class)));
        }

        if (btnFinalizar != null) {
            btnFinalizar.setOnClickListener(v -> {
                SharedPreferences spAcesso = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);

                if (usarModoMockParaPrints && spAcesso.getString("auth_token", null) == null) {
                    spAcesso.edit().putString("auth_token", "token_fake_print").apply();
                }

                if (spAcesso.getString("auth_token", null) == null) {
                    Toast.makeText(this, "Faça login para finalizar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }

                if (itensCarrinho == null || itensCarrinho.isEmpty()) {
                    Toast.makeText(this, "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean precisaDeGrau = false;
                for (Produto produto : itensCarrinho) {
                    String metadados = (produto.getNome() + " " + produto.getDescricao()).toLowerCase();
                    if (metadados.contains("grau") || metadados.contains("armação") || metadados.contains("lente")) {
                        precisaDeGrau = true;
                        break;
                    }
                }

                if (precisaDeGrau) {
                    SharedPreferences spReceita = getSharedPreferences("DADOS_RECEITA", MODE_PRIVATE);
                    float esfOD = spReceita.getFloat("OD_esferico", -99f);

                    if (esfOD == -99f) {
                        Toast.makeText(this, "Contém itens de grau! Por favor, envie sua receita primeiro.", Toast.LENGTH_LONG).show();
                        Intent intentReceita = new Intent(this, AnexarReceitaActivity.class);
                        startActivity(intentReceita);
                    } else {
                        avancarParaResumo();
                    }
                } else {
                    avancarParaResumo();
                }
            });
        }
    }

    private void carregarItensDoCarrinho() {
        itensCarrinho.clear();

        if (usarModoMockParaPrints) {
            String uriOculos1 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos1;
            String uriOculos2 = "android.resource://" + getPackageName() + "/" + R.drawable.oculos2;

            itensCarrinho.add(new Produto("Ana Hickmann NINA 4", 559.90, "Lente: Visão Simples", uriOculos1));
            itensCarrinho.add(new Produto("Atitude AT11", 321.30, "Lente: Anti-Reflexo", uriOculos2));
        }
    }

    private void avancarParaResumo() {
        Toast.makeText(this, "Processando pedido...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ResumoCompraActivity.class);
        startActivity(intent);
    }

    private void configurarMenuInferior(BottomNavigationView bottomMenu) {
        if (bottomMenu == null) return;

        bottomMenu.setSelectedItemId(R.id.nav_carrinho);
        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            }
            if (id == R.id.nav_modelos) {
                startActivity(new Intent(this, CategoriasActivity.class));
                finish();
                return true;
            }
            if (id == R.id.nav_carrinho) return true;
            if (id == R.id.nav_mais) {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                return true;
            }
            return false;
        });
    }

    private void calcularTotal() {
        double total = 0;
        for (Produto p : itensCarrinho) total += p.getPreco();
        txtTotal.setText(String.format("Total: R$ %.2f", total));
    }
}