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

import { BeneficiariosService } from './beneficiarios.service';
import { BeneficiariosEffects } from './store/beneficiarios.effects';
import { BeneficiariosPage } from './containers/beneficiarios/beneficiarios.page';
import * as fromBeneficiarios from './store/beneficiarios.reducer';

@NgModule({
  declarations: [BeneficiariosPage],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    StoreModule.forFeature(fromBeneficiarios.beneficiariosFeatureKey, fromBeneficiarios.reducer),
    EffectsModule.forFeature([BeneficiariosEffects])
  ],
  providers: [
    BeneficiariosService
  ]
})
export class BeneficiariosModule { }
