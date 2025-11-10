import { Slider } from './../slider-component/slider-component';
import { Component } from '@angular/core';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';
import { InfoOfertas } from '../info-ofertas/info-ofertas';
import { Produtos } from '../produtos/produtos';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MenuSuperior, Footer, Slider, InfoOfertas, Produtos],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home {

}
