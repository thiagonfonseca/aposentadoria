import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { of } from "rxjs";
import { catchError, map, mergeMap } from "rxjs/operators";
import { ToastrService } from 'ngx-toastr';

import { BeneficiariosService } from "../beneficiarios.service";
import * as fromBeneficiariosActions from './beneficiarios.actions';
import { Beneficiario } from "src/app/shared/models/beneficario.model";
import { Store } from "@ngrx/store";
import { BeneficiariosState } from "./beneficiarios.reducer";

@Injectable()
export class BeneficiariosEffects {

    constructor(private actions$: Actions,
        private store: Store<BeneficiariosState>,
        private beneficiarioService: BeneficiariosService, 
        private toastr: ToastrService) {
    }

    loadBeneficiarios$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromBeneficiariosActions.getListBeneficiarios),
            mergeMap(() => this.beneficiarioService.listAll()),
            catchError((err, caught$) => {
                this.store.dispatch(fromBeneficiariosActions.loadBeneficiariosFailed());
                return caught$;
            }),
            map((entities: Beneficiario[]) => fromBeneficiariosActions.loadBeneficiarios({ list: entities }))
        )
    );

    getBeneficiarioByCpf$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromBeneficiariosActions.getBeneficiarioByCpf),
            mergeMap(({cpf}) => this.beneficiarioService.getByCpf(cpf)),
            catchError((err, caugth$) => {
                this.store.dispatch(fromBeneficiariosActions.getBeneficiarioByCpfFailed());
                this.toastr.error("Não há beneficiário cadastrado com este CPF!");
                return caugth$;
            }),
            map((entity: Beneficiario) => fromBeneficiariosActions.loadBeneficiario({ beneficiario: entity }))
        )
    );

    saveBeneficiario$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromBeneficiariosActions.saveBeneficiario),
            mergeMap(({beneficiario}) => this.beneficiarioService.saveBeneficiario(beneficiario)),
            catchError((err, caugth$) => {
                this.store.dispatch(fromBeneficiariosActions.saveBeneficiarioFailed());
                this.toastr.error("Não foi possível cadastrar o beneficiário!");
                return caugth$;
            }),
            map(() => {
                this.beneficiarioService.setClearForm();
                this.toastr.success("Beneficiário cadastrado com sucesso!");
                return fromBeneficiariosActions.getListBeneficiarios();
            })
        )
    );

    updateBeneficiario$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromBeneficiariosActions.updateBeneficiario),
            mergeMap((action) => {
                const beneficiario = {
                    id: action.beneficiario.changes.id,
                    nome: action.beneficiario.changes.nome,
                    cpf: action.beneficiario.changes.cpf,
                    email: action.beneficiario.changes.email,
                    saldo: action.beneficiario.changes.saldo,
                    anos: action.beneficiario.changes.anos
                };
                return this.beneficiarioService.updateBeneficiario(action.beneficiario.id, beneficiario)
            }), 
            catchError((err, caugth$) => {
                this.store.dispatch(fromBeneficiariosActions.updateBeneficiarioFailed());
                this.toastr.error("Não foi possível atualizar o beneficiário!");
                return caugth$;            
            }),
            map(() => {
                this.beneficiarioService.setClearForm();
                this.toastr.success("Beneficiário atualizado com sucesso!");
                return fromBeneficiariosActions.getListBeneficiarios();
            })
        )
    );

    deleteBeneficiario$ = createEffect(() => this.actions$
        .pipe(
            ofType(fromBeneficiariosActions.deleteBeneficiario),
            mergeMap(({id}) => this.beneficiarioService.deleteBeneficiario(id)),
            catchError((err, caugth$) => {
                this.store.dispatch(fromBeneficiariosActions.deleteBeneficiarioFailed());
                this.toastr.error("Não foi possível excluir o beneficiário!");
                return caugth$;
            }),
            map(() => {
                this.beneficiarioService.setClearForm();
                this.toastr.success("Beneficiário excluído com sucesso!");
                return fromBeneficiariosActions.getListBeneficiarios();
            })
        )
    );

}