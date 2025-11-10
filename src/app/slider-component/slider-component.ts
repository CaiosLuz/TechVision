import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-slider',
  standalone: true,
  imports: [CommonModule, NgbCarouselModule],
  templateUrl: './slider-component.html',
  styleUrls: ['./slider-component.css']
})
export class Slider {
}
