import { Injectable } from '@angular/core';

export interface IConfiguracaoOculos {
  produto?: any;
  tipoLente?: string;
  espessura?: string;
  receita?: {
    esfericoOD?: number | null;
    esfericoOE?: number | null;
    cilindricoOD?: number | null;
    cilindricoOE?: number | null;
    eixoOD?: number | null;
    eixoOE?: number | null;
  };
}

@Injectable({
  providedIn: 'root',
})

export class ConfiguracaoOculos {
  private configuracao: IConfiguracaoOculos = {
    produto: undefined,
    tipoLente: undefined,
    espessura: undefined,
    receita: undefined
  };

  setProduto(produto: any) {
    this.configuracao.produto = produto;
  }

  setTipoLente(tipo: string) {
    this.configuracao.tipoLente = tipo;
  }

  setEspessura(espessura: string) {
    this.configuracao.espessura = espessura;
  }

  setReceita(receita: IConfiguracaoOculos['receita']) {
    this.configuracao.receita = receita;
  }

  getConfiguracao() {
    return this.configuracao;
  }

  reset() {
    this.configuracao = { produto: undefined, tipoLente: undefined, espessura: undefined, receita: undefined };
  }
}
