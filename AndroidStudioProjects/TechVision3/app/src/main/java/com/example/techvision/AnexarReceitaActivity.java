package com.example.techvision;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techvision.model.IAOftalmoResponse;
import com.example.techvision.network.IAOftalmoService;
import com.example.techvision.network.RetrofitClient;

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
    private TextView txtStatusOCR;

    private boolean receitaAnexada = false;
    private IAOftalmoResponse dadosIA = null;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anexar_receita);

        Button btnAvancar =
                findViewById(R.id.btnProximaEtapa3);

        btnAvancar.setEnabled(false);

        View root =
                findViewById(R.id.main_anexar);

        View topBar =
                findViewById(R.id.topBarReceita);

        if(root != null){

            ViewCompat.setOnApplyWindowInsetsListener(
                    root,
                    (v,windowInsets)->{

                        Insets insets =
                                windowInsets.getInsets(
                                        WindowInsetsCompat
                                                .Type
                                                .systemBars()
                                );

                        if(topBar != null){

                            ViewGroup.MarginLayoutParams params =
                                    (ViewGroup.MarginLayoutParams)
                                            topBar.getLayoutParams();

                            params.topMargin =
                                    insets.top;

                            topBar.setLayoutParams(
                                    params
                            );
                        }

                        return windowInsets;
                    }
            );
        }

        imgPreview =
                findViewById(R.id.imgPreview);

        progressBarAnalisando =
                findViewById(
                        R.id.progressBarAnalisando
                );

        txtStatusOCR =
                findViewById(
                        R.id.txtStatusOCR
                );

        findViewById(R.id.btnAnexar)
                .setOnClickListener(v->{

                    Intent intent =
                            new Intent(
                                    Intent.ACTION_GET_CONTENT
                            );

                    intent.setType(
                            "image/*"
                    );

                    startActivityForResult(
                            intent,
                            PICK_IMAGE_REQUEST
                    );

                });

        findViewById(R.id.btnProximaEtapa3)
                .setOnClickListener(v->{

                    if(
                            !receitaAnexada
                                    ||
                                    dadosIA == null
                    ){

                        Toast.makeText(
                                this,
                                "Aguarde análise da receita",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    Intent anterior =
                            getIntent();

                    Intent proxima =
                            new Intent(
                                    this,
                                    ResumoCompraActivity.class
                            );

                    proxima.putExtra(
                            "NOME_OCULOS",
                            anterior.getStringExtra(
                                    "NOME_OCULOS"
                            )
                    );

                    proxima.putExtra(
                            "PRECO_OCULOS",
                            anterior.getDoubleExtra(
                                    "PRECO_OCULOS",
                                    0
                            )
                    );

                    proxima.putExtra(
                            "TIPO_LENTE",
                            anterior.getStringExtra(
                                    "TIPO_LENTE"
                            )
                    );

                    proxima.putExtra(
                            "ESPESSURA",
                            anterior.getStringExtra(
                                    "ESPESSURA"
                            )
                    );

                    proxima.putExtra(
                            "IMAGEM_OCULOS",
                            anterior.getStringExtra(
                                    "IMAGEM_OCULOS"
                            )
                    );

                    proxima.putExtra(
                            "OD_ESFERICO",
                            dadosIA.getOd().getEsferico()
                    );

                    proxima.putExtra(
                            "OD_CILINDRICO",
                            dadosIA.getOd().getCilindrico()
                    );

                    proxima.putExtra(
                            "OD_EIXO",
                            dadosIA.getOd().getEixo()
                    );

                    proxima.putExtra(
                            "OE_ESFERICO",
                            dadosIA.getOe().getEsferico()
                    );

                    proxima.putExtra(
                            "OE_CILINDRICO",
                            dadosIA.getOe().getCilindrico()
                    );

                    proxima.putExtra(
                            "OE_EIXO",
                            dadosIA.getOe().getEixo()
                    );

                    startActivity(
                            proxima
                    );

                });

    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ){

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if(
                requestCode ==
                        PICK_IMAGE_REQUEST
                        &&
                        resultCode ==
                                RESULT_OK
                        &&
                        data != null
        ){

            Uri selectedImageUri =
                    data.getData();

            receitaAnexada =
                    false;

            dadosIA =
                    null;

            findViewById(
                    R.id.btnProximaEtapa3
            ).setEnabled(false);

            txtStatusOCR.setVisibility(
                    View.VISIBLE
            );

            txtStatusOCR.setText(
                    "Imagem selecionada"
            );

            txtStatusOCR.setTextColor(
                    Color.GRAY
            );

            com.bumptech.glide.Glide
                    .with(this)
                    .load(selectedImageUri)
                    .into(imgPreview);

            processarImagemComIA(
                    selectedImageUri
            );
        }
    }

    private void processarImagemComIA(
            Uri imageUri
    ){

        progressBarAnalisando
                .setVisibility(
                        View.VISIBLE
                );

        txtStatusOCR.setText(
                "Consultando OCR..."
        );

        txtStatusOCR.setTextColor(
                Color.parseColor(
                        "#666666"
                )
        );

        try{

            InputStream is =
                    getContentResolver()
                            .openInputStream(
                                    imageUri
                            );

            File tempFile =
                    File.createTempFile(
                            "receita",
                            ".jpg",
                            getCacheDir()
                    );

            FileOutputStream fos =
                    new FileOutputStream(
                            tempFile
                    );

            byte[] buffer =
                    new byte[1024];

            int len;

            while(
                    (len=is.read(buffer))
                            != -1
            ){

                fos.write(
                        buffer,
                        0,
                        len
                );

            }

            fos.close();

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(
                                    "image/*"
                            ),
                            tempFile
                    );

            MultipartBody.Part body =
                    MultipartBody.Part
                            .createFormData(
                                    "file",
                                    tempFile.getName(),
                                    requestFile
                            );

            IAOftalmoService service =
                    RetrofitClient
                            .getClientIA()
                            .create(
                                    IAOftalmoService.class
                            );

            service.analisarReceita(body)
                    .enqueue(
                            new Callback<IAOftalmoResponse>() {

                                @Override
                                public void onResponse(
                                        Call<IAOftalmoResponse> call,
                                        Response<IAOftalmoResponse> response
                                ) {

                                    progressBarAnalisando
                                            .setVisibility(
                                                    View.GONE
                                            );

                                    if(
                                            response.isSuccessful()
                                                    &&
                                                    response.body()!=null
                                    ){

                                        dadosIA =
                                                response.body();

                                        receitaAnexada =
                                                true;

                                        findViewById(
                                                R.id.btnProximaEtapa3
                                        ).setEnabled(
                                                true
                                        );

                                        txtStatusOCR.setText(
                                                "✓ Receita interpretada com sucesso"
                                        );

                                        txtStatusOCR.setTextColor(
                                                Color.parseColor(
                                                        "#2E7D32"
                                                )
                                        );

                                    }else{

                                        txtStatusOCR.setText(
                                                "❌ Não foi possível interpretar a receita"
                                        );

                                        txtStatusOCR.setTextColor(
                                                Color.RED
                                        );

                                    }

                                }

                                @Override
                                public void onFailure(
                                        Call<IAOftalmoResponse> call,
                                        Throwable t
                                ) {

                                    progressBarAnalisando
                                            .setVisibility(
                                                    View.GONE
                                            );

                                    txtStatusOCR.setText(
                                            "⚠ Falha de comunicação com servidor OCR"
                                    );

                                    txtStatusOCR.setTextColor(
                                            Color.RED
                                    );

                                }

                            });

        }catch(Exception e){

            progressBarAnalisando
                    .setVisibility(
                            View.GONE
                    );

            txtStatusOCR.setText(
                    "Erro ao processar imagem"
            );

            txtStatusOCR.setTextColor(
                    Color.RED
            );

        }

    }

}