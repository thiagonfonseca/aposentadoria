import { Component, OnInit, OnDestroy, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable, Subject, Subscription } from 'rxjs';
import { DataSource } from '@angular/cdk/table';
import { MatTableDataSource } from '@angular/material/table';
import { Update } from '@ngrx/entity';

import { Beneficiario } from 'src/app/shared/models/beneficario.model';
import { BeneficiariosService } from '../../beneficiarios.service';

import * as fromBeneficiariosSelectors from '../../store/beneficiarios.selectors';
import * as fromBeneficiariosActions from '../../store/beneficiarios.actions';
import * as fromBeneficiariosStore from '../../store/beneficiarios.reducer';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-beneficiarios',
  templateUrl: './beneficiarios.page.html',
  styleUrls: ['./beneficiarios.page.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BeneficiariosPage implements OnInit, OnDestroy {

  dataSource: DataSource<Beneficiario>;
  
  isLoading$: Observable<boolean>;

  error$: Observable<string | null>;
  
  beneficiarios$: Observable<Beneficiario[]>;

  beneficiario: Beneficiario;

  editing: boolean = false;

  private componentDestroyed$ = new Subject();

  form: FormGroup;

  clearForm = new Subscription();

  public displayedColumns = [
    'nome', 'cpf', 'email', 'saldo', 'anos', 'actions'
  ];

  constructor(private store: Store<fromBeneficiariosStore.BeneficiariosState>, private fb: FormBuilder, private beneficiariosService: BeneficiariosService) { 
    this.form = this.createForm();
  }

  ngOnInit(): void {
    this.store.dispatch(fromBeneficiariosActions.getListBeneficiarios());
    this.beneficiarios$ = this.store.select(fromBeneficiariosSelectors.beneficiarios);
    this.beneficiarios$.pipe(takeUntil(this.componentDestroyed$));
    this.isLoading$ = this.store.select(fromBeneficiariosSelectors.isLoading);
    this.error$ = this.store.select(fromBeneficiariosSelectors.error);
    this.loadData();
    this.clearForm = this.beneficiariosService.clearForm$.subscribe(
      () => this.form.reset()
    )
  }

  loadData() {
    this.beneficiarios$.subscribe(data => {
      this.dataSource = new MatTableDataSource<Beneficiario>(data);
    });
  }

  ngOnDestroy() {
    this.componentDestroyed$.next();
    this.componentDestroyed$.unsubscribe();
  }

  createForm = (item?: Beneficiario) => {
    if (item) {
      return new FormGroup({
        id: new FormControl(item.id),
        nome: new FormControl(item.nome),
        cpf: new FormControl(item.cpf),
        email: new FormControl(item.email),
        saldo: new FormControl(item.saldo),
        anos: new FormControl(item.anos)
      });
    }
    return new FormGroup({
      id: new FormControl(),
      nome: new FormControl(''),
      cpf: new FormControl(''),
      email: new FormControl(''),
      saldo: new FormControl(''),
      anos: new FormControl('')
    })
  }

  saveBeneficiario() {
    this.beneficiario = this.form.value;
    if (this.beneficiario.id === null) {
      this.store.dispatch(fromBeneficiariosActions.saveBeneficiario({beneficiario: this.beneficiario}));
    } else {
      const updateBeneficiario: Update<Beneficiario>= {
        id: this.beneficiario.id,
        changes: this.beneficiario
      }
      this.store.dispatch(fromBeneficiariosActions.updateBeneficiario({beneficiario: updateBeneficiario}));
    }
    this.loadData();
  }

  editBeneficiario(item) {
    this.editing = true;
    this.form = this.createForm(item);
  }

  deleteBeneficiario(id: number) {
    if (confirm("Deseja excluir este beneficiário?")) {
      this.store.dispatch(fromBeneficiariosActions.deleteBeneficiario({ id }));
      this.loadData();  
    }
  }

  cancel() {
    this.editing = false;
    this.form = this.createForm();
  }

}