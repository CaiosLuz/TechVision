import { Component } from '@angular/core';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-slider-component',
  standalone: true,
  imports: [NgbCarouselModule],
  templateUrl: './slider-component.html',
  styleUrls: ['./slider-component.css'],
})
export class SliderComponent {
  images = [
    'images/slider/slide1.jpg',
    'images/slider/slide2.jpg',
    'images/slider/slide3.jpg',
  ];
}
