package com.example.techvision;

import java.io.Serializable;

public class Produto implements Serializable {
    private String nome;
    private String preco;
    private String descontoPix;
    private int imagemRes;
    private String avaliacao; // Novo campo para o detalhe

    public Produto(String nome, String preco, String descontoPix, int imagemRes, String avaliacao) {
        this.nome = nome;
        this.preco = preco;
        this.descontoPix = descontoPix;
        this.imagemRes = imagemRes;
        this.avaliacao = avaliacao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getPreco() { return preco; }
    public int getImagemRes() { return imagemRes; }
    public String getAvaliacao() { return avaliacao; }
}