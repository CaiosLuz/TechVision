package com.example.techvision.network;

import com.example.techvision.model.IAOftalmoResponse; // Você precisará criar este Model também

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IAOftalmoService {

    @Multipart
    @POST("analisar")
    Call<IAOftalmoResponse> analisarReceita(@Part MultipartBody.Part file);

}