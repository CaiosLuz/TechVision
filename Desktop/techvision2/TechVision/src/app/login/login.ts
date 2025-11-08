import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  email = '';
  senha = '';
  carregando = false;
  mensagemErro = '';

  emailErro = '';
  senhaErro = '';
  erro: any;

  constructor(private auth: AuthService, private router: Router) {}

  async login() {
    // Reset mensagens de erro
    this.emailErro = '';
    this.senhaErro = '';
    this.mensagemErro = '';

    // Validação dos campos
    if (!this.email) {
      this.emailErro = 'O campo Email é obrigatório';
    }
    if (!this.senha) {
      this.senhaErro = 'O campo Senha é obrigatório';
    }
    if (!this.email || !this.senha) return;

    this.carregando = true;

    try {
      const user = await this.auth.login(this.email, this.senha);
      console.log('Usuário logado:', user);
      this.router.navigate(['/']); // redireciona para home
    } catch (err: any) {
      this.mensagemErro = err.message || 'Login inválido';
    } finally {
      this.carregando = false;
    }
  }
}
