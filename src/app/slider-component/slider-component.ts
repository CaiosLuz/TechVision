import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // <-- importante
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-slider',
  standalone: true,
  imports: [CommonModule, NgbCarouselModule], // <-- adicione CommonModule
  templateUrl: './slider-component.html',
  styleUrls: ['./slider-component.css']
})
export class Slider {
  images = [
    {
      src: 'images/slider/slide1.png',
      title: 'Garanta 40% OFF',
      text: 'Compre um óculos infantil e ganhe 40% off em um par para você.'
    },
    {
      src: 'images/slider/slide1.png',
      title: 'Garanta 40% OFF',
      text: 'Compre um óculos infantil e ganhe 40% off em um par para você.'
    },
    {
      src: 'images/slider/slide1.png',
      title: 'Garanta 40% OFF',
      text: 'Compre um óculos infantil e ganhe 40% off em um par para você.'
    }
  ];
}