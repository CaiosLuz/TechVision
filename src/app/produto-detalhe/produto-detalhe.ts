import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProdutoService } from '../services/produto';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

@Component({
  selector: 'app-produto',
  standalone: true,
  imports: [CommonModule, FormsModule, MenuSuperior, Footer],
  templateUrl: './produto-detalhe.html',
  styleUrls: ['./produto-detalhe.css'],
})
export class InfoProduto implements OnInit {
  produto: any;
  imagens: string[] = [];
  imagemPrincipal = '';
  quantidade = 1;

  constructor(
    private produtoService: ProdutoService,
    private route: ActivatedRoute,
    private router: Router,
    private configOculos: ConfiguracaoOculos
  ) {}

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.produto = await this.produtoService.getProduto(id);
    this.imagens = this.produto?.imagens_produto?.map((i: any) => i.url) || [];
    this.imagemPrincipal = this.imagens[0] || '';
  }

  alterarQuantidade(valor: number) {
    this.quantidade = Math.max(1, this.quantidade + valor);
  }

  adicionarAoCarrinho() {
    alert(`Adicionado ${this.quantidade}x ${this.produto.nome} ao carrinho 🛒`);
  }

  comprarAgora() {
    const produtoCompleto = {
    ...this.produto,
    imagens: this.imagens,              // array de URLs
    imagemPrincipal: this.imagemPrincipal // URL principal
  };

  this.configOculos.setProduto(produtoCompleto);
  this.router.navigate(['/tipo-lente', this.produto.id]);
  }
}
