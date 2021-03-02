import { createFeatureSelector, createSelector } from "@ngrx/store";

import * as fromStore from './beneficiarios.reducer';

const beneficiariosSelector = createFeatureSelector<fromStore.BeneficiariosState>(fromStore.beneficiariosFeatureKey);

export const isLoading = createSelector(beneficiariosSelector, fromStore.selectIsLoading);
export const beneficiarios = createSelector(beneficiariosSelector, fromStore.selectAll);
export const error = createSelector(beneficiariosSelector, fromStore.selectError);
export const beneficiario = createSelector(beneficiariosSelector, fromStore.selectBeneficiario);