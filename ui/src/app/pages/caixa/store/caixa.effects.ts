import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap } from "rxjs/operators";
import { ToastrService } from 'ngx-toastr';

import { CaixaService } from "../caixa.service";
import * as fromCaixaActions from './caixa.actions';
import { Store } from "@ngrx/store";
import { CaixaState } from "./caixa.reducer";
import { combineLatest } from "rxjs";
import { BeneficiariosService } from "../../beneficiarios/beneficiarios.service";

@Injectable()
export class CaixaEffects {

    constructor(private actions$: Actions,
        private store: Store<CaixaState>,
        private beneficiarioService: BeneficiariosService,
        private caixaService: CaixaService, 
        private toastr: ToastrService) {
    }

    initialCaixa$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromCaixaActions.initialCaixa),
            map(() => fromCaixaActions.loadCaixa({list: []}))
        )
    );

    saveCaixa$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromCaixaActions.saveCaixa),
            mergeMap(({caixa}) => {
                console.log(caixa);
                return this.caixaService.saveCaixa(caixa)
            }),
            catchError((err, caught$) => {
                this.toastr.error("Não foi possível cadastrar o aporte!");
                this.store.dispatch(fromCaixaActions.saveCaixaFailed());
                return caught$;
            }),
            map(() => {
                this.caixaService.setClearForm();
                this.toastr.success("Aporte registrado com sucesso!");
                return fromCaixaActions.initialCaixa();
            })
        )
    );

    getCaixaByCpf$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromCaixaActions.getCaixaByCpf),
            mergeMap(({cpf}) => 
                combineLatest([
                    this.beneficiarioService.getByCpf(cpf),
                    this.caixaService.getByCpf(cpf)
                ])
            ),
            catchError((err, caugth$) => {
                this.toastr.error("Não há aporte registrado para este CPF!");
                this.store.dispatch(fromCaixaActions.getCaixaByCpfFailed());
                return caugth$;
            }),
            map((entities: any[]) => {
                for (let i = 0; i < entities[1].length; i++) {
                    entities[1][i].id = i+1;
                }
                return fromCaixaActions.loadCaixa({ list: entities[1] })
            })
        )
    );

    calculaAposentadoriaByCpf$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromCaixaActions.calculaAposentadoria),
            mergeMap(({cpf}) =>
                combineLatest([
                    this.beneficiarioService.getByCpf(cpf),
                    this.caixaService.calculaAposentadoria(cpf)
                ])
            ),
            catchError((err, caugth$) => {
                this.toastr.error("Não foi possível calcular o benefício!");
                this.store.dispatch(fromCaixaActions.calculaAposentadoriaFailed());
                return caugth$;
            }),
            map((entity: any[]) => fromCaixaActions.loadResultado({ resultado: entity[1] }))
        )
    );

}
