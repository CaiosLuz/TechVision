import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-superior',
  imports: [],
  templateUrl: './menu-superior.html',
  styleUrl: './menu-superior.css',
})
export class MenuSuperior {

  constructor(private router: Router) {}
  irHome() {
    this.router.navigate(['/']);
  }
}
