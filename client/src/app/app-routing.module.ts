import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component'; // Importar HomeComponent
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: '', component: LoginComponent }, // Ruta predeterminada
  { path: 'home', component: HomeComponent }, // Ruta para el componente "home"
  { path: 'register', component: RegisterComponent }, // Ruta de registro
  { path: '**', redirectTo: '' } // Redirigir rutas no válidas al login
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
