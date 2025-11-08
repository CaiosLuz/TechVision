import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OcrService {
  private apiUrl = 'http://127.0.0.1:8000/analisar';

  constructor(private http: HttpClient) {}

  analisarImagem(imagem: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', imagem);
    return this.http.post(this.apiUrl, formData);
  }
}
