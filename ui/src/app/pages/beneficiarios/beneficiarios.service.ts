import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Beneficiario } from 'src/app/shared/models/beneficario.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BeneficiariosService {

  private clearFormSource = new Subject<boolean>();
  clearForm$ = this.clearFormSource.asObservable();

  constructor(private http: HttpClient) { }

  public setClearForm() {
    this.clearFormSource.next(true);
  }

  saveBeneficiario(beneficiario: Beneficiario) {
    return this.http.post(`${environment.webBeneficiarioServiceUrl}`, beneficiario, {responseType: 'text'});
  }

  updateBeneficiario(id: any, beneficiario: Beneficiario) {
    return this.http.put(`${environment.webBeneficiarioServiceUrl}/${id}`, beneficiario, {responseType: 'text'});
  }

  listAll(): Observable<Beneficiario[]> {
    return this.http.get<any>(`${environment.webBeneficiarioServiceUrl}`);
  }

  getById(id: number) {
    return this.http.get<any>(`${environment.webBeneficiarioServiceUrl}/${id}`);
  }

  getByCpf(cpf: string) {
    return this.http.get<any>(`${environment.webBeneficiarioServiceUrl}/cpf/${cpf}`);
  }

  deleteBeneficiario(id: number) {
    return this.http.delete<any>(`${environment.webBeneficiarioServiceUrl}/${id}`);
  }

}
