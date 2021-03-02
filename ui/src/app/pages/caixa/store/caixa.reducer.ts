import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { createReducer, on } from "@ngrx/store";

import { Caixa } from "src/app/shared/models/caixa.model";

import * as fromCaixaAction from './caixa.actions';

export const caixaFeatureKey = 'caixa';

export interface CaixaState extends EntityState<Caixa> {
    isLoading: boolean;
    error: boolean;
    resultado: number;
};

export const adapter: EntityAdapter<Caixa> = createEntityAdapter<Caixa>();

export const caixaInitialState: CaixaState = adapter.getInitialState({
    isLoading: false,
    error: false,
    resultado: 0
});

export const reducer = createReducer(
    caixaInitialState,
    on(fromCaixaAction.initialCaixa, state => adapter.setAll([], {
        ...state,
        isLoading: true        
    })),
    on(fromCaixaAction.getCaixaByCpf, state => ({
        ...state,
        isLoading: true,
        error: false
    })),
    on(fromCaixaAction.loadCaixa, (state, action) => adapter.setAll(action.list, {
        ...state,
        isLoading: false,
        error: false
    })),
    on(fromCaixaAction.saveCaixa, (state, action) => adapter.addOne(action.caixa, state)),
    on(fromCaixaAction.loadResultado, (state, { resultado }) => ({
        ...state,
        isLoading: false,
        error: false,
        resultado
    }))
);

export const { selectAll } = adapter.getSelectors();

export const selectIsLoading = (state: CaixaState) => state.isLoading;
export const selectError = (state: CaixaState) => state.error;
export const resultado = (state: CaixaState) => state.resultado;