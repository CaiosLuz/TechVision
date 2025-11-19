import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { ProdutoService } from '../services/produto';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

@Component({
  selector: 'app-tipo-lente',
  standalone: true,
  imports: [CommonModule, MenuSuperior, Footer],
  templateUrl: './tipo-lente.html',
  styleUrls: ['./tipo-lente.css'],
})
export class TipoLente implements OnInit {
  produto: any;
  imagens: string[] = [];
  imagemPrincipal = '';
  tipoSelecionado: string = '';
  tratamentosSelecionados: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private produtoService: ProdutoService,
    private configOculos: ConfiguracaoOculos
  ) {}

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.produto = await this.produtoService.getProduto(id);
    this.imagens = this.produto?.imagens_produto?.map((i: any) => i.url) || [];
    this.imagemPrincipal = this.imagens[0] || '';
  }

  escolherLente(tipo: string) {
    this.tipoSelecionado = tipo;
    this.configOculos.setProduto(this.produto);
    this.configOculos.setTipoLente(tipo);
  }

  selecionarTipo(tipo: string) {
    this.tipoSelecionado = tipo;
    this.configOculos.setTipoLente(tipo);
    this.configOculos.setProduto(this.produto)
  }

  toggleTratamento(nome: string) {
    if (this.tratamentosSelecionados.includes(nome)) {
      this.tratamentosSelecionados =
        this.tratamentosSelecionados.filter(t => t !== nome);
    } else {
      this.tratamentosSelecionados.push(nome);
    }

    // salva no service
    this.configOculos.setTratamentos(this.tratamentosSelecionados);

    console.log('Tratamentos selecionados:', this.tratamentosSelecionados);
  }

  irParaEspessura() {
    if (!this.tipoSelecionado) return;

    this.configOculos.setTipoLente(this.tipoSelecionado);
    this.configOculos.setTratamentos(this.tratamentosSelecionados);
    this.configOculos.setProduto(this.produto);

    this.router.navigate(['/espessura-lente', this.produto.id]);
  }
}