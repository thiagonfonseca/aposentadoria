import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Caixa } from 'src/app/shared/models/caixa.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CaixaService {

  private clearFormSource = new Subject<boolean>();
  clearForm$ = this.clearFormSource.asObservable();

  constructor(private http: HttpClient) { }

  public setClearForm() {
    this.clearFormSource.next(true);
  }

  saveCaixa(caixa: Caixa) {
    return this.http.post(`${environment.webCaixaServiceUrl}`, caixa, {responseType: 'text'});
  }

  getByCpf(cpf: string): Observable<Caixa[]> {
    return this.http.get<any>(`${environment.webCaixaServiceUrl}/cpf/${cpf}`);
  }

  calculaAposentadoria(cpf: string): Observable<any> {
    return this.http.get<any>(`${environment.webCaixaServiceUrl}/calcula/${cpf}`);
  }

}
