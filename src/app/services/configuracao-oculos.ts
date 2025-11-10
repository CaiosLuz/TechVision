import { Injectable } from '@angular/core';

export interface IConfiguracaoOculos {
  produto?: any;
  tipoLente?: string;
  espessura?: string;
}

@Injectable({
  providedIn: 'root',
})

export class ConfiguracaoOculos {
  private configuracao: IConfiguracaoOculos = {
    produto: undefined,
    tipoLente: undefined,
    espessura: undefined,
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

  getConfiguracao() {
    return this.configuracao;
  }

  reset() {
    this.configuracao = { produto: undefined, tipoLente: undefined, espessura: undefined };
  }
}
