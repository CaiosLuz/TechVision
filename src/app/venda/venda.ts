// src/app/venda/venda.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProdutoService } from '../services/produto';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Router } from '@angular/router';

@Component({
  selector: 'app-venda',
  standalone: true,
  imports: [CommonModule, MenuSuperior],
  templateUrl: './venda.html',
  styleUrls: ['./venda.css'],
})

export class VendaComponent implements OnInit {
  carrinho: any[] = [];
  meiosPagamento = ['Pix', 'Cartão', 'Boleto'];
  pagamentoSelecionado: string[] = [];

  constructor(private produtoService: ProdutoService, private router: Router) {}

  async ngOnInit() {
    const produto = await this.produtoService.getProduto(1);
    this.carrinho = [{
      ...produto,
      quantidade: 1
    }];
  }

  total() {
    return this.carrinho.reduce((acc, item) => acc + item.preco * item.quantidade, 0);
  }

  incrementarQuantidade(index: number) {
    this.carrinho[index].quantidade++;
  }

  decrementarQuantidade(index: number) {
    if (this.carrinho[index].quantidade > 1) this.carrinho[index].quantidade--;
  }

  removerProduto(index: number) {
    this.carrinho.splice(index, 1);
  }

  togglePagamento(meio: string, event: any) {
    if (event.target.checked) {
      this.pagamentoSelecionado.push(meio);
    } else {
      this.pagamentoSelecionado = this.pagamentoSelecionado.filter(m => m !== meio);
    }
  }

  finalizarDepois() {
    this.router.navigate(['/']);
  }

  continuarComprando() {
    this.router.navigate(['/']);
  }

  finalizarPagamento() {
    if (this.pagamentoSelecionado.length === 0) {
      alert('Selecione pelo menos um meio de pagamento!');
      return;
    }
    alert('Pagamento finalizado via: ' + this.pagamentoSelecionado.join(', '));
    // Aqui você pode salvar no Supabase usando tabelas vendas e venda_produto
  }
}