package com.example.techvision.model;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {

    // Esses nomes devem ser iguais às chaves que o seu Back-end/API retorna no JSON
    private int id;
    private String data;
    private String status;
    private double total;
    private List<Produto> itens; // Caso sua API também mande a lista de produtos do pedido

    // Construtor vazio (necessário para várias bibliotecas de desserialização)
    public Pedido() {
    }

    // Construtor completo
    public Pedido(int id, String data, String status, double total, List<Produto> itens) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.total = total;
        this.itens = itens;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public void setItens(List<Produto> itens) {
        this.itens = itens;
    }
}