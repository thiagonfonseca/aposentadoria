import { DataSource } from '@angular/cdk/table';
import { ChangeDetectionStrategy, Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable, Subject, Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

import { Caixa } from 'src/app/shared/models/caixa.model';

import * as fromCaixaActions from '../../store/caixa.actions';
import * as fromCaixaStore from '../../store/caixa.reducer';
import * as fromCaixaSelectors from '../../store/caixa.selectors';

import { CaixaService } from '../../caixa.service';
import { Beneficiario } from 'src/app/shared/models/beneficario.model';
import { MatTableDataSource } from '@angular/material/table';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-caixa',
  templateUrl: './caixa.page.html',
  styleUrls: ['./caixa.page.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CaixaPage implements OnInit, OnDestroy {

  dataSource: DataSource<Caixa>;
  
  isLoading$: Observable<boolean>;

  error$: Observable<boolean>;
  
  caixa$: Observable<Caixa[]>;

  aportes: Caixa[];

  caixa: Caixa;

  private componentDestroyed$ = new Subject();

  form: FormGroup;

  clearForm = new Subscription();

  validateForm: boolean = false;

  public displayedColumns = [
    'cpf', 'aporte'
  ];

  constructor(private store: Store<fromCaixaStore.CaixaState>, 
      private toastr: ToastrService,
      private fb: FormBuilder, 
      private caixaService: CaixaService) { 
    this.form = this.createForm();
  }

  ngOnInit(): void {
    this.loadData();
    this.clearForm = this.caixaService.clearForm$.subscribe(
      () => this.form.reset()
    )
  }

  ngOnDestroy() {
    this.componentDestroyed$.next();
    this.componentDestroyed$.unsubscribe();
  }

  loadData() {
    this.store.dispatch(fromCaixaActions.initialCaixa());
    this.caixa$ = this.store.select(fromCaixaSelectors.aportes);
    this.isLoading$ = this.store.select(fromCaixaSelectors.isLoading);
    this.error$ = this.store.select(fromCaixaSelectors.error);
    this.validateForm = false;
    this.caixa$.subscribe(data => {
      this.dataSource = new MatTableDataSource<Caixa>(data);
    });
    this.form = this.createForm();
  }

  createForm = (item?: Caixa) => {
    return new FormGroup({
      cpf: new FormControl(''),
      aporte: new FormControl(''),
      resultado: new FormControl({value: '', disabled: true})
    })
  }

  saveCaixa() {
    this.caixa = {
      id: null,
      cpf: this.form.get('cpf').value,
      aporte: this.form.get('aporte').value
    };
    this.store.dispatch(fromCaixaActions.saveCaixa({caixa: this.caixa}));
    this.loadData();
  }

  getCaixaByCpf() {
    const cpf = this.form.get('cpf').value;
    this.store.dispatch(fromCaixaActions.getCaixaByCpf({cpf}));
    this.caixa$ = this.store.select(fromCaixaSelectors.aportes);
    this.caixa$.subscribe(dataC => {
      this.aportes = dataC;
      this.dataSource = new MatTableDataSource<Caixa>(this.aportes);
      this.validateForm = true;
      this.form.get('resultado').setValue(0);
    }, error => {
      this.toastr.error("Não há aportes para este beneficiário!");
      this.loadData();
    });
  }

  calculaAposentadoriaByCpf() {
    const cpf = this.form.get('cpf').value;
    this.store.dispatch(fromCaixaActions.calculaAposentadoria({cpf}));
    const resultado$ = this.store.select(fromCaixaSelectors.resultado);
    resultado$.subscribe(dataR => {
      this.form.get('resultado').setValue(dataR);
    }, error => {
      this.toastr.error("Não foi possível gerar o resultado!");
      this.loadData();
    });
  }

  cancel() {
    this.loadData();
  }

}
