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
    this.configOculos.setProduto(this.produto);
    this.router.navigate(['/selecionar-lente', this.produto.id]);
  }
}