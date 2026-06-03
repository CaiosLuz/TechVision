package com.example.techvision.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL =
            "http://56.125.23.244:8080/";

    private static final String IA_BASE_URL =
            "https://ia-de-leitura-receita-oftalmologista-1.onrender.com/";

    private static Retrofit retrofit = null;
    private static Retrofit retrofitIA = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientIA() {

        if (retrofitIA == null) {

            OkHttpClient client =
                    new OkHttpClient.Builder()

                            .connectTimeout(
                                    60,
                                    TimeUnit.SECONDS
                            )

                            .readTimeout(
                                    60,
                                    TimeUnit.SECONDS
                            )

                            .writeTimeout(
                                    60,
                                    TimeUnit.SECONDS
                            )

                            .build();

            retrofitIA =
                    new Retrofit.Builder()

                            .baseUrl(
                                    IA_BASE_URL
                            )

                            .client(client)

                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            )

                            .build();
        }

        return retrofitIA;
    }
}