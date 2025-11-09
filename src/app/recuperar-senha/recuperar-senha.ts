import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-recuperar-senha',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './recuperar-senha.html',
  styleUrls: ['../login/login.css'] // podemos reaproveitar o CSS do login
})
export class RecuperarSenha {
  email = '';
  carregando = false;
  emailErro = '';
  mensagemSucesso = '';
  mensagemErro = '';

  constructor(private auth: AuthService) {}

  async recuperarSenha() {
    this.emailErro = '';
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    if (!this.email) {
      this.emailErro = 'O campo Email é obrigatório';
      return;
    }

    this.carregando = true;

    try {
      await this.auth.recuperarSenha(this.email);
      this.mensagemSucesso = 'Link de recuperação enviado! Verifique seu email.';
    } catch (err: any) {
      this.mensagemErro = err.message || 'Erro ao enviar link';
    } finally {
      this.carregando = false;
    }
  }
}
