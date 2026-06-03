// Altere seu arquivo AuthService.java para isso:
package com.example.techvision.network;

import com.example.techvision.model.LoginRequest;
import okhttp3.ResponseBody; // Importe este cara
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<ResponseBody> logar(@Body LoginRequest loginRequest);
}