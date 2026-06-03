package com.example.techvision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.techvision.model.LoginRequest;
import com.example.techvision.network.AuthService;
import com.example.techvision.network.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etSenha;
    Button btnLogin;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnLogin = findViewById(R.id.btnLogin);

        authService = RetrofitClient.getClient().create(AuthService.class);

        btnLogin.setOnClickListener(v -> efetuarLogin());
    }

    private void efetuarLogin() {
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, senha);
        Call<ResponseBody> call = authService.logar(loginRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String tokenCompleto = response.body().string();

                        // Extrai nome do email (ex: joao@gmail.com -> Joao)
                        String nomeExtraido = email.split("@")[0];
                        nomeExtraido = nomeExtraido.substring(0, 1).toUpperCase() + nomeExtraido.substring(1);

                        SharedPreferences sp = getSharedPreferences("DADOS_ACESSO", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("auth_token", tokenCompleto);
                        editor.putString("usuario_nome", nomeExtraido); // Salva o nome
                        editor.apply();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } catch (Exception e) {
                        Log.e("AWS_ERROR", "Erro ao processar: " + e.getMessage());
                    }
                } else {
                    etSenha.setError("E-mail ou senha incorretos");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}