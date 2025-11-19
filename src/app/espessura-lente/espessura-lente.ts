import { Component, OnInit } from '@angular/core';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-espessura-lente',
  imports: [MenuSuperior, Footer, CommonModule],
  templateUrl: './espessura-lente.html',
  styleUrl: './espessura-lente.css',
})
export class EspessuraLente implements OnInit{
  produto: any;
  imagemPrincipal = '';
  tipoLente: string = '';
  tratamentosSelecionados: string[] = [];
  espessuraSelecionada: string = '';

  constructor(private configOculos: ConfiguracaoOculos, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    const config = this.configOculos.getConfiguracao();

    console.log('Configuração atual:', config);

    this.produto = config.produto;
    this.tipoLente = config.tipoLente ?? '';
    this.tratamentosSelecionados = config.tratamentos ?? [];
    this.imagemPrincipal = config.produto?.imagens_produto?.[0]?.url 
      || 'images/tipo-lente/produto-secundario.png';
    this.espessuraSelecionada = config.espessura ?? '';
  }

  selecionarEspessura(espessura: string) {
    this.espessuraSelecionada = espessura;
    this.configOculos.setEspessura(espessura);
  }

  irParaReceita() {
    this.router.navigate(['/anexar-receita',  this.produto.id]);
  }
}
