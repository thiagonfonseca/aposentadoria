import { createFeatureSelector, createSelector } from "@ngrx/store";

import * as fromStore from './caixa.reducer';

const caixaSelector = createFeatureSelector<fromStore.CaixaState>(fromStore.caixaFeatureKey);

export const isLoading = createSelector(caixaSelector, fromStore.selectIsLoading);
export const aportes = createSelector(caixaSelector, fromStore.selectAll);
export const error = createSelector(caixaSelector, fromStore.selectError);
export const resultado = createSelector(caixaSelector, fromStore.resultado);