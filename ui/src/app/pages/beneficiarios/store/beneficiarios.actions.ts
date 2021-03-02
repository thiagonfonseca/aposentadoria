import { Update } from '@ngrx/entity';
import { createAction, props } from "@ngrx/store";
import { Beneficiario } from "src/app/shared/models/beneficario.model";

export const getListBeneficiarios = createAction(
    '[Beneficiarios] Get List Beneficiarios'
);

export const getBeneficiario = createAction(
    '[Beneficiarios] Get Beneficiario'
);

export const loadBeneficiarios = createAction(
    '[Beneficiarios] Load Beneficiarios',
    props<{ list: Beneficiario[] }>()
);

export const loadBeneficiariosFailed = createAction('[Beneficiarios] Load Beneficiarios Failed');

export const loadBeneficiario = createAction(
    '[Beneficiarios] Load Beneficiario',
    props<{ beneficiario: Beneficiario }>()
);

export const loadBeneficiarioFailed = createAction('[Beneficiarios] Load Beneficiario Failed');

export const getBeneficiarioByCpf = createAction(
    '[Beneficiarios] Get Beneficiario by CPF',
    props<{ cpf: string }>()
);

export const getBeneficiarioByCpfFailed = createAction('[Beneficiarios] Get Beneficiario by CPF Failed');

export const saveBeneficiario = createAction(
    '[Beneficiarios] Save Beneficiario',
    props<{ beneficiario: Beneficiario }>()
);

export const saveBeneficiarioFailed = createAction('[Beneficiarios] Save Beneficiario Failed');

export const updateBeneficiario = createAction(
    '[Beneficiarios] Update Beneficiario',
    props<{ beneficiario: Update<Beneficiario> }>()
);

export const updateBeneficiarioFailed = createAction('[Beneficiarios] Update Beneficiario Failed');

export const deleteBeneficiario = createAction(
    '[Beneficiarios] Delete Beneficiario',
    props<{ id:number }>()
);

export const deleteBeneficiarioFailed = createAction('[Beneficiarios] Delete Beneficiario Failed');