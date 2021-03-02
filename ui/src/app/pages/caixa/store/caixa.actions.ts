import { createAction, props } from "@ngrx/store";
import { Caixa } from "src/app/shared/models/caixa.model";

export const initialCaixa = createAction(
  '[Caixa] Initializing Caixa'  
);

export const loadCaixa = createAction(
    '[Caixa] Load Caixa',
    props<{ list: Caixa[] }>()
);

export const loadCaixaFailed = createAction('[Caixa] Load Caixa Failed');

export const loadResultado = createAction(
    '[Caixa] Get Resultado',
    props<{ resultado: number }>()
);

export const loadResultadoFailed = createAction('[Caixa] Get Resultado Failed');

export const saveCaixa = createAction(
    '[Caixa] Save Aporte',
    props<{ caixa: Caixa }>()
);

export const saveCaixaFailed = createAction('[Caixa] Save Aporte Failed');

export const getCaixaByCpf = createAction(
    '[Caixa] List Aporte by CPF',
    props<{ cpf: string }>()
);

export const getCaixaByCpfFailed = createAction('[Caixa] List Aporte By CPF Failed');

export const calculaAposentadoria = createAction(
    '[Caixa] Calcula Aposentadoria by CPF',
    props<{ cpf: string }>()
);

export const calculaAposentadoriaFailed = createAction('[Caixa] Calcula Aposentadoria By CPF Failed');
