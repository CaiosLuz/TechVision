package com.example.techvision;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnexarReceitaActivity extends AppCompatActivity {

    // Constante para identificar a seleção de imagem
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; // Guarda o endereço da imagem selecionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexar_receita);

        // 1. Ajuste da Barra de Notificações (Rodar logo no início)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // 2. Referências dos botões
        Button btnAnexar = findViewById(R.id.btnAnexar);
        Button btnProximaEtapa3 = findViewById(R.id.btnProximaEtapa3);

        btnAnexar.setOnClickListener(v -> abrirGaleria());

        btnProximaEtapa3.setOnClickListener(v -> {
            // Removi a verificação de (imageUri == null) para teste
            Toast.makeText(this, "Avançando para o resumo (modo de teste)", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ResumoCompraActivity.class);
            startActivity(intent);
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Esse método recebe o resultado da galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Feedback para o usuário
            Toast.makeText(this, "Imagem selecionada!", Toast.LENGTH_SHORT).show();

            // Dica: Você poderia colocar um ImageView no seu XML e fazer:
            // ImageView preview = findViewById(R.id.seu_id_de_preview);
            // preview.setImageURI(imageUri);
        }
    }
}