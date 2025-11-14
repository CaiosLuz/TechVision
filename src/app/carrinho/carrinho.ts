import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

@Component({
  selector: 'app-carrinho',
  standalone: true,
  imports: [CommonModule, MenuSuperior, Footer],
  templateUrl: './carrinho.html',
  styleUrls: ['./carrinho.css']
})
export class Carrinho {

  produto: any;
  tipoLente?: string;
  espessura?: string;
  receita: any = null;

  constructor(private router: Router, private configOculos: ConfiguracaoOculos) {}

  ngOnInit() {
    const config = this.configOculos.getConfiguracao();
    console.log("CONFIG:", config);
    this.produto = config.produto;
    this.tipoLente = config.tipoLente;
    this.espessura = config.espessura;
    this.receita = config.receita; 
  }

  finalizarCompra() {
    this.router.navigate(['/venda']);
  }
}
