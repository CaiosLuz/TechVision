package com.example.techvision.model;

import com.google.gson.annotations.SerializedName;

public class IAOftalmoResponse {

    // Indica ao Gson que no JSON a chave se chama "OD" em maiúsculo
    @SerializedName("OD")
    private DadosOlho od;

    @SerializedName("OE")
    private DadosOlho oe;

    public DadosOlho getOd() { return od; }
    public void setOd(DadosOlho od) { this.od = od; }

    public DadosOlho getOe() { return oe; }
    public void setOe(DadosOlho oe) { this.oe = oe; }

    // Subclasse para mapear os dados internos de cada olho
    public static class DadosOlho {
        @SerializedName("esferico")
        private double esferico;

        @SerializedName("cilindrico")
        private double cilindrico;

        @SerializedName("eixo")
        private int eixo;

        public double getEsferico() { return esferico; }
        public void setEsferico(double esferico) { this.esferico = esferico; }

        public double getCilindrico() { return cilindrico; }
        public void setCilindrico(double cilindrico) { this.cilindrico = cilindrico; }

        public int getEixo() { return eixo; }
        public void setEixo(int eixo) { this.eixo = eixo; }
    }
}