import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './registrar.html',
  styleUrls: ['../login/login.css']
})
export class RegisterComponent {
  email = '';
  senha = '';
  carregando = false;
  mensagemErro = '';
  mensagemSucesso = '';

  constructor(private auth: AuthService) {}

  async registrar() {
    if (!this.email || !this.senha) {
      this.mensagemErro = 'Preencha todos os campos';
      return;
    }

    this.carregando = true;
    this.mensagemErro = '';
    this.mensagemSucesso = '';

    try {
      const user = await this.auth.signUp(this.email, this.senha);
      console.log('Usuário registrado:', user);
      this.mensagemSucesso = 'Cadastro realizado com sucesso!';
    } catch (err: any) {
      this.mensagemErro = err.message || 'Erro ao cadastrar usuário';
    } finally {
      this.carregando = false;
    }
  }
}
