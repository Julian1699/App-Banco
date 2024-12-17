import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LayoutComponent } from './layout/layout.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', component: LoginComponent }, // Página de login
  { path: 'register', component: RegisterComponent }, // Página de registro
  {
    path: '', // Rutas con layout
    component: LayoutComponent,
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'another-route', component: HomeComponent }, // Más rutas aquí
    ],
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
