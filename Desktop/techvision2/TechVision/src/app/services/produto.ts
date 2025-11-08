// src/app/services/produto.service.ts
import { Injectable } from '@angular/core';
import { AuthService } from './auth';

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  constructor(private auth: AuthService) {}

  async getProduto(id: number) {
    const { data, error } = await this.auth.client
      .from('produtos')
      .select(`
        *,
        imagens_produto(url)
      `)
      .eq('id', id)
      .maybeSingle();

    if (error) {
      console.error(error);
      throw error;
    }

    return { ...data, cores: data?.cores || [] };
  }
}