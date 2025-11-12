import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';

@Component({
  selector: 'app-carrinho',
  standalone: true,
  imports: [CommonModule, MenuSuperior, Footer],
  templateUrl: './carrinho.html',
  styleUrls: ['./carrinho.css']
})
export class Carrinho {
  produto = {
    nome: 'Atitude AT1718M - Preto Fosco',
    preco: 321.30
  };

  tipoLente = 'Grau';
  espessura = 'Fina';

  receita = {
    longe: {
      OD: { esferico: -2.00, cilindrico: 0.00, eixo: '', dp: '' },
      OE: { esferico: -2.00, cilindrico: 0.00, eixo: '', dp: '' }
    },
    perto: {
      OD: { esferico: +1.00, cilindrico: 0.00, eixo: '', dp: '' },
      OE: { esferico: +1.00, cilindrico: 0.00, eixo: '', dp: '' }
    }
  };

  constructor(private router: Router) {}

  finalizarCompra() {
    this.router.navigate(['/pagamento']);
  }
}
