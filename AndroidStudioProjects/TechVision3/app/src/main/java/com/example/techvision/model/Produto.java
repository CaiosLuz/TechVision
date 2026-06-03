package com.example.techvision.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private String id;
    private String nome;
    private String descricao;
    private double preco;
    private String imagemUrl;

    // CONSTRUTOR PADRÃO - OBRIGATÓRIO PARA O RETROFIT/GSON
    public Produto() {
    }

    // CONSTRUTOR CUSTOMIZADO - Para seus Mocks locais
    public Produto(String nome, double preco, String descricao, String imagemUrl) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getImagemUrl() { return imagemUrl; }
    public String getDescricao() { return descricao; }

    // MÉTODO CORRIGIDO: Validação inteligente por palavras-chave
    public boolean precisaDeReceita() {
        String busca = "";

        // Evita NullPointerException concatenando apenas se os campos não forem nulos
        if (this.nome != null) {
            busca += this.nome.toLowerCase();
        }
        if (this.descricao != null) {
            busca += " " + this.descricao.toLowerCase();
        }

        // Se encontrar qualquer termo que exija grau, retorna true
        return busca.contains("grau") || busca.contains("armação") || busca.contains("lente");
    }
}