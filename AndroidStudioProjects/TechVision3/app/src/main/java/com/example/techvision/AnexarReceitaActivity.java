package com.example.techvision;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techvision.model.IAOftalmoResponse;
import com.example.techvision.network.IAOftalmoService;
import com.example.techvision.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnexarReceitaActivity extends AppCompatActivity {

    private ImageView imgPreview;
    private ProgressBar progressBarAnalisando;
    private boolean receitaAnexada = false;
    private IAOftalmoResponse dadosIA = null;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexar_receita);

        Button btnAvançar = findViewById(R.id.btnProximaEtapa3);
        btnAvançar.setEnabled(false);

        // 1. Ajuste de Insets
        View root = findViewById(R.id.main_anexar);
        View topBar = findViewById(R.id.topBarReceita);
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                if (topBar != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) topBar.getLayoutParams();
                    params.topMargin = insets.top;
                    topBar.setLayoutParams(params);
                }
                return windowInsets;
            });
        }

        // 2. Inicialização
        imgPreview = findViewById(R.id.imgPreview);
        progressBarAnalisando = findViewById(R.id.progressBarAnalisando);

        findViewById(R.id.btnAnexar).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // 3. Botão Avançar
        findViewById(R.id.btnProximaEtapa3).setOnClickListener(v -> {
            if (!receitaAnexada || dadosIA == null) {
                Toast.makeText(this, "Aguarde a análise da receita pela IA.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intentAnterior = getIntent();
            Intent intentProxima = new Intent(this, ResumoCompraActivity.class);

            // Repassando dados do produto
            intentProxima.putExtra("NOME_OCULOS", intentAnterior.getStringExtra("NOME_OCULOS"));
            intentProxima.putExtra("PRECO_OCULOS", intentAnterior.getDoubleExtra("PRECO_OCULOS", 0.0));
            intentProxima.putExtra("TIPO_LENTE", intentAnterior.getStringExtra("TIPO_LENTE"));
            intentProxima.putExtra("ESPESSURA", intentAnterior.getStringExtra("ESPESSURA"));
            intentProxima.putExtra("IMAGEM_OCULOS", intentAnterior.getStringExtra("IMAGEM_OCULOS"));

            // Dados REAIS da IA
            intentProxima.putExtra("OD_ESFERICO", dadosIA.getOd().getEsferico());
            intentProxima.putExtra("OD_CILINDRICO", dadosIA.getOd().getCilindrico());
            intentProxima.putExtra("OD_EIXO", dadosIA.getOd().getEixo());
            intentProxima.putExtra("OE_ESFERICO", dadosIA.getOe().getEsferico());
            intentProxima.putExtra("OE_CILINDRICO", dadosIA.getOe().getCilindrico());
            intentProxima.putExtra("OE_EIXO", dadosIA.getOe().getEixo());

            startActivity(intentProxima);
        });
    }

    // --- Métodos de processamento (Fora do onCreate, dentro da classe) ---

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // 1. Reset visual e funcional do botão
            // Desativamos o botão ANTES de começar a carregar a nova imagem
            Button btnAvançar = findViewById(R.id.btnProximaEtapa3);
            btnAvançar.setEnabled(false);
            receitaAnexada = false;
            dadosIA = null;

            // 2. Carrega a imagem com Glide
            // Isso coloca a imagem no ImageView sem "piscar" ou pular
            imgPreview.setVisibility(View.VISIBLE);
            com.bumptech.glide.Glide.with(this)
                    .load(selectedImageUri)
                    .into(imgPreview);

            // 3. Chama a função que envia para a IA
            // A ProgressBar dentro do FrameLayout (abaixo) aparecerá sobre a imagem
            processarImagemComIA(selectedImageUri);
        }
    }

    private void processarImagemComIA(Uri imageUri) {
        progressBarAnalisando.setVisibility(View.VISIBLE);
        try {
            InputStream is = getContentResolver().openInputStream(imageUri);
            File tempFile = File.createTempFile("receita", ".jpg", getCacheDir());
            FileOutputStream fos = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) fos.write(buffer, 0, len);
            fos.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), tempFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", tempFile.getName(), requestFile);

            IAOftalmoService service = RetrofitClient.getClientIA().create(IAOftalmoService.class);
            service.analisarReceita(body).enqueue(new Callback<IAOftalmoResponse>() {
                @Override
                public void onResponse(Call<IAOftalmoResponse> call, Response<IAOftalmoResponse> response) {
                    progressBarAnalisando.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null) {
                        dadosIA = response.body();
                        receitaAnexada = true;

                        // HABILITA O BOTÃO
                        findViewById(R.id.btnProximaEtapa3).setEnabled(true);

                        Toast.makeText(AnexarReceitaActivity.this, "Receita lida com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        // MANTÉM DESATIVADO E ALERTA O ERRO
                        findViewById(R.id.btnProximaEtapa3).setEnabled(false);
                        Toast.makeText(AnexarReceitaActivity.this, "Erro na leitura da IA", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IAOftalmoResponse> call, Throwable t) {
                    progressBarAnalisando.setVisibility(View.GONE);
                    findViewById(R.id.btnProximaEtapa3).setEnabled(false); // MANTÉM DESATIVADO
                    Toast.makeText(AnexarReceitaActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            progressBarAnalisando.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }
}