package com.example.techvision.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // O IP que deu "200 OK" no seu print
    private static final String BASE_URL = "http://56.125.23.244:8080/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientIA() {
        return new Retrofit.Builder()
                .baseUrl("https://ia-de-leitura-receita-oftalmologista-1.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}