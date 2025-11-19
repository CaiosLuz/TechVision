import { Component, OnInit } from '@angular/core';
import { OcrService } from './../services/ocr';
import { MenuSuperior } from '../menu-superior/menu-superior';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ConfiguracaoOculos } from '../services/configuracao-oculos';

@Component({
  selector: 'app-anexar-receita',
  standalone: true,
  imports: [MenuSuperior, CommonModule, FormsModule],
  templateUrl: './anexar-receita.html',
  styleUrls: ['./anexar-receita.css']
})
export class AnexarReceita implements OnInit{
  imagemSelecionada: File | null = null;
  resultado: any = null;
  carregando = false;
  preencherManual = false;
  tratamentosSelecionados: string[] = [];

  esfericoOD: number | null = null;
  esfericoOE: number | null = null;
  cilindricoOD: number | null = null;
  cilindricoOE: number | null = null;
  eixoOD: number | null = null;
  eixoOE: number | null = null;

  produto: any;
  tipoLente: string = '';
  espessura: string = '';

  constructor(private ocr: OcrService, private router: Router, private configOculos: ConfiguracaoOculos) {}

  ngOnInit() {
    const config = this.configOculos.getConfiguracao();

    this.produto = config.produto;
    this.tipoLente = config.tipoLente ?? '';
    this.espessura = config.espessura ?? '';
    this.tratamentosSelecionados = config.tratamentos ?? [];
  }

  onFileSelected(event: any) {
    this.imagemSelecionada = event.target.files[0];
  }

  enviarImagem() {
    if (!this.imagemSelecionada) return;
    this.carregando = true;

    this.ocr.analisarImagem(this.imagemSelecionada).subscribe({
      next: res => {
        this.resultado = res.resultado;

        if (res.resultado.OD) {
          this.esfericoOD = res.resultado.OD.esferico;
          this.cilindricoOD = res.resultado.OD.cilindrico;
          this.eixoOD = res.resultado.OD.eixo;
        }
        if (res.resultado.OE) {
          this.esfericoOE = res.resultado.OE.esferico;
          this.cilindricoOE = res.resultado.OE.cilindrico;
          this.eixoOE = res.resultado.OE.eixo;
        }

        this.salvarReceita();
        this.carregando = false;
      },
      error: err => {
        console.error(err);
        this.carregando = false;
      }
    });
  }

  salvarReceita() {
    this.configOculos.setReceita({
      esfericoOD: this.esfericoOD,
      esfericoOE: this.esfericoOE,
      cilindricoOD: this.cilindricoOD,
      cilindricoOE: this.cilindricoOE,
      eixoOD: this.eixoOD,
      eixoOE: this.eixoOE
    })
  }

  salvarManual() {
    console.log('Valores manuais salvos:', {
      esfericoOD: this.esfericoOD,
      esfericoOE: this.esfericoOE,
      cilindricoOD: this.cilindricoOD,
      cilindricoOE: this.cilindricoOE,
      eixoOD: this.eixoOD,
      eixoOE: this.eixoOE
    });
    alert('Dados salvos manualmente!');
  }
  
  pularEtapa() {
    this.preencherManual = false;
    this.esfericoOD = null;
    this.configOculos.setReceita(null);
    this.router.navigate(['/venda']);
  }

  irParaCarrinho() {
    this.router.navigate(['/carrinho']);
  }
}
