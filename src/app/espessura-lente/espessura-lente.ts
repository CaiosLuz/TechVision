import { Component, OnInit } from '@angular/core';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

@Component({
  selector: 'app-espessura-lente',
  imports: [MenuSuperior, Footer],
  templateUrl: './espessura-lente.html',
  styleUrl: './espessura-lente.css',
})
export class EspessuraLente implements OnInit{
  produto: any;
  imagemPrincipal = '';
  tipoLente: string = '';

  constructor(private configOculos: ConfiguracaoOculos, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.configOculos.setEspessura('');
    console.log('Configuração atual:', this.configOculos.getConfiguracao());
    const { produto, tipoLente } = this.configOculos.getConfiguracao();

    this.produto = produto;
    this.tipoLente = this.configOculos.getConfiguracao().tipoLente ?? '';
    this.imagemPrincipal = produto?.imagens_produto?.[0]?.url || 'images/tipo-lente/produto-secundario.png';
  }

  selecionarEspessura(espessura: string) {
    this.configOculos.setEspessura(espessura);
    console.log('Espessura selecionada:', espessura);

    const produto = this.configOculos.getConfiguracao().produto;

    this.router.navigate(['/anexar-receita', produto.id]);
  }
}
