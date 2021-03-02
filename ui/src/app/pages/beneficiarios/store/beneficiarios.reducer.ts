import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { createReducer, on } from "@ngrx/store";

import { Beneficiario } from "src/app/shared/models/beneficario.model";

import * as fromBeneficiariosAction from './beneficiarios.actions';

export const beneficiariosFeatureKey = 'beneficiarios';

export interface BeneficiariosState extends EntityState<Beneficiario> {
    isLoading: boolean;
    error: string | null;
    beneficiario: Beneficiario;
};

export const adapter: EntityAdapter<Beneficiario> = createEntityAdapter<Beneficiario>();

export const beneficiarioInitialState: BeneficiariosState = adapter.getInitialState({
    isLoading: true,
    error: null,
    beneficiario: null
})

export const reducer = createReducer(
    beneficiarioInitialState,
    on(fromBeneficiariosAction.saveBeneficiario, (state, action) => adapter.addOne(action.beneficiario, state)),
    on(fromBeneficiariosAction.updateBeneficiario, (state, action) => adapter.updateOne(action.beneficiario, state)),
    on(fromBeneficiariosAction.deleteBeneficiario, (state, action) => adapter.removeOne(action.id, state)),
    on(fromBeneficiariosAction.loadBeneficiarios, (state, action) => adapter.setAll(action.list, {
        ...state,
        isLoading: false
    })),
    on(fromBeneficiariosAction.loadBeneficiario, (state, action) => ({
        ...state,
        isLoading: false,
        beneficiario: action.beneficiario
    })),
    on(fromBeneficiariosAction.getListBeneficiarios, (state, action) => adapter.setAll([], {
        ...state,
        isLoading: true
    })),
    on(fromBeneficiariosAction.getBeneficiario, state => ({
        ...state,
        isLoading: false,
        beneficiario: null
    }))
);

export const { selectAll } = adapter.getSelectors();

export const selectIsLoading = (state: BeneficiariosState) => state.isLoading;
export const selectError = (state: BeneficiariosState) => state.error;
export const selectBeneficiario = (state: BeneficiariosState) => state.beneficiario;