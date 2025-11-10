import { RouterLink  } from '@angular/router';
import { Component, OnInit, signal } from '@angular/core';
import { AuthService } from '../services/auth';
import { CommonModule, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [CommonModule, DecimalPipe, RouterLink],
  templateUrl: './produtos.html',
  styleUrls: ['./produtos.css']
})
export class Produtos implements OnInit {
  produtos = signal<any[]>([]);

  constructor(private auth: AuthService) {}

  async ngOnInit() {
    try {
      const supabase = this.auth.client;
      const { data, error } = await supabase
        .from('produtos')
        .select(`
          *,
          imagens_produto (
            url
          )
        `)
        .order('avaliacao', { ascending: false })
        .limit(4);

      if (error) throw error;
      this.produtos.set(data);
    } catch (err) {
      console.error('Erro ao buscar produtos:', err);
    }
  }
}
