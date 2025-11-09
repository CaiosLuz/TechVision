import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ProdutoService } from '../services/produto';
import { MenuSuperior } from '../menu-superior/menu-superior';

@Component({
  selector: 'app-produto',
  standalone: true,
  imports: [CommonModule, MenuSuperior], // <- Adicione o CommonModule aqui
  templateUrl: './produto-detalhe.html',
  styleUrls: ['./produto-detalhe.css'],
})
export class InfoProduto implements OnInit {
  produto: any;
  imagens: string[] = [];
  imagemPrincipal = '';

  constructor(
    private produtoService: ProdutoService,
    private route: ActivatedRoute
  ) {}

  async ngOnInit() {
    try {
      const idStr = this.route.snapshot.paramMap.get('id')!;
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.produto = await this.produtoService.getProduto(id);

      // Configura imagens
      this.imagens = this.produto?.imagens_produto?.map((i: any) => i.url) || [];
      this.imagemPrincipal = this.imagens[0] || '';
      console.log('Produto:', this.produto);
    } catch (err) {
      console.error(err);
    }
  }
}
