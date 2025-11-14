import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Router } from '@angular/router';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

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

  constructor(
    private router: Router,
    private configOculos: ConfiguracaoOculos
  ) {}

  ngOnInit() {
    const config = this.configOculos.getConfiguracao();

    console.log("CONFIG VENDA =>", config);

    // caso o usuário tente abrir venda sem configurar nada
    if (!config || !config.produto) {
      this.carrinho = [];
      return;
    }

    // monta item com tudo que veio dos passos anteriores
    this.carrinho = [
      {
        ...config.produto,
        tipoLente: config.tipoLente,
        espessura: config.espessura,
        receita: config.receita,
        quantidade: 1,
      }
    ];
  }

  total() {
    return this.carrinho.reduce(
      (acc, item) => acc + item.preco * item.quantidade,
      0
    );
  }

  incrementarQuantidade(index: number) {
    this.carrinho[index].quantidade++;
  }

  decrementarQuantidade(index: number) {
    if (this.carrinho[index].quantidade > 1) {
      this.carrinho[index].quantidade--;
    }
  }

  removerProduto(index: number) {
    this.carrinho.splice(index, 1);
  }

  togglePagamento(meio: string, event: any) {
    if (event.target.checked) {
      this.pagamentoSelecionado.push(meio);
    } else {
      this.pagamentoSelecionado = this.pagamentoSelecionado.filter(
        (m) => m !== meio
      );
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

    alert(
      'Pagamento finalizado via: ' + this.pagamentoSelecionado.join(', ')
    );
  }
}
