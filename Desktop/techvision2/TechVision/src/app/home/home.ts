import { SliderComponent } from './../slider-component/slider-component';
import { Component } from '@angular/core';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { Footer } from '../footer/footer';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MenuSuperior, Footer, SliderComponent],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

}
