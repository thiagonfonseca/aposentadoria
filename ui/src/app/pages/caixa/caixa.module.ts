import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';

import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

import { CaixaService } from './caixa.service';
import { CaixaEffects } from './store/caixa.effects';
import { CaixaPage } from './containers/caixa/caixa.page';
import * as fromCaixa from './store/caixa.reducer';

@NgModule({
  declarations: [CaixaPage],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    StoreModule.forFeature(fromCaixa.caixaFeatureKey, fromCaixa.reducer),
    EffectsModule.forFeature([CaixaEffects])
  ],
  providers: [
    CaixaService
  ]
})
export class CaixaModule { }
