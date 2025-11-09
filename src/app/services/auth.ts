// src/app/services/auth.service.ts
import { Injectable } from '@angular/core';
import { createClient, SupabaseClient } from '@supabase/supabase-js';

@Injectable({ providedIn: 'root' }) // permite injetar o serviço em qualquer componente
export class AuthService {
  private supabase: SupabaseClient;

  constructor() {
    // Substitua pelos dados do seu projeto Supabase
    this.supabase = createClient(
      'https://oyagtdnhodtpmtpzrabh.supabase.co',
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im95YWd0ZG5ob2R0cG10cHpyYWJoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjIyOTI2ODAsImV4cCI6MjA3Nzg2ODY4MH0.iKVeJg9Yw2dMV9OXJN_EIkIFcHPlnp1gnLblLr_Vay0'
    );
  }

  get client() {
    return this.supabase;
  }

   // Login com email e senha
  async login(email: string, password: string) {
    const { data, error } = await this.supabase.auth.signInWithPassword({
      email,
      password
    });
    if (error) throw error;
    return data;
  }

  // Logout
  async logout() {
    const { error } = await this.supabase.auth.signOut();
    if (error) throw error;
  }

  // Registrar usuário
  async signUp(email: string, password: string) {
    const { data, error } = await this.supabase.auth.signUp({
      email,
      password
    });
    if (error) throw error;
    return data;
  }

  // Pegar usuário logado
  getUser() {
    return this.supabase.auth.getUser();
  }

  async recuperarSenha(email: string) {
  const { data, error } = await this.supabase.auth.resetPasswordForEmail(email);
  if (error) throw error;
  return data;
}
}