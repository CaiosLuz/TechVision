package com.example.techvision.network;

import com.example.techvision.model.Produto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProdutoService {

    // O endpoint costuma ser "produtos" ou "products"
    // O @Header é usado para passar o token de autenticação
    @GET("produtos")
    Call<List<Produto>> listarProdutos(@Header("Authorization") String token);
}