import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BeneficiariosPage } from './pages/beneficiarios/containers/beneficiarios/beneficiarios.page';
import { CaixaPage } from './pages/caixa/containers/caixa/caixa.page';

const routes: Routes = [
  { path: 'beneficiarios', component: BeneficiariosPage },
  { path: 'caixa', component: CaixaPage },
  { path: '', pathMatch: 'full', redirectTo: 'beneficiarios' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
