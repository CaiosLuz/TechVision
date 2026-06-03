package com.example.techvision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Adapte os imports abaixo para os seus pacotes reais de Pedido/Service
// import com.example.techvision.model.Pedido;
// import com.example.techvision.adapter.PedidoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ComprasActivity extends AppCompatActivity {

    private RecyclerView rvPedidos;
    // private PedidoAdapter adapter;
    // private List<Pedido> listaPedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        // 1. Configuração de Insets para notch/barra de status
        View mainCompras = findViewById(R.id.main_compras);
        ViewCompat.setOnApplyWindowInsetsListener(mainCompras, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Configuração do RecyclerView
        rvPedidos = findViewById(R.id.rvPedidos);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));

        // Inicialize seu adapter aqui quando o tiver criado:
        // adapter = new PedidoAdapter(listaPedidos);
        // rvPedidos.setAdapter(adapter);

        // 3. Configuração do Menu Inferior Padronizado
        BottomNavigationView bottomMenu = findViewById(R.id.bottomMenu);
        configurarMenuInferior(bottomMenu);

        carregarPedidosDaApi();
    }

    private void configurarMenuInferior(BottomNavigationView bottomMenu) {
        if (bottomMenu == null) return;

        // Como essa tela pode ser aberta a partir do perfil ("nav_mais"), deixamos o ícone desmarcado
        // ou você pode marcar o nav_mais como ativo se preferir que ela represente essa aba.
        bottomMenu.getMenu().findItem(R.id.nav_inicio).setChecked(false);

        bottomMenu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // Fecha a tela de compras para não acumular na pilha
                return true;
            }
            if (id == R.id.nav_carrinho) {
                startActivity(new Intent(this, CarrinhoActivity.class));
                finish();
                return true;
            }
            if (id == R.id.nav_modelos) {
                startActivity(new Intent(this, CategoriasActivity.class));
                finish();
                return true;
            }
            if (id == R.id.nav_mais) {
                // Se o usuário clicar em "Mais", ele volta para a Home abrindo o Drawer de lá
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
    }

    private void carregarPedidosDaApi() {
        SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
        String token = sp.getString("auth_token", "");

        // Chamada da API de listagem de pedidos...
    }
}