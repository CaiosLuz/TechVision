// src/app/services/venda.service.ts
import { Injectable } from '@angular/core';
import { AuthService } from './auth';

@Injectable({ providedIn: 'root' })
export class VendaService {
  constructor(private auth: AuthService) {}

  // Cria uma venda no banco
  async criarVenda(total: number, forma_pagamento: string) {
    const { data, error } = await this.auth.client
      .from('vendas')
      .insert([{ total, forma_pagamento, status: 'em andamento', data: new Date() }])
      .select()
      .maybeSingle();
    if (error) throw error;
    return data;
  }

  // Adiciona produtos a uma venda existente
  async adicionarProduto(vendaId: number, produtoId: number, quantidade: number, precoUnit: number) {
    const { data, error } = await this.auth.client
      .from('venda_produtos')
      .insert([{ venda_id: vendaId, produto_id: produtoId, quantidade, preco_unit: precoUnit }])
      .select();
    if (error) throw error;
    return data;
  }

  // Pegar vendas (opcional, para histórico)
  async getVendas() {
    const { data, error } = await this.auth.client
      .from('vendas')
      .select('*');
    if (error) throw error;
    return data;
  }
}
