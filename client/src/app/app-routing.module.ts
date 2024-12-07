import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component'; // Importar HomeComponent

const routes: Routes = [
  { path: '', component: LoginComponent }, // Ruta predeterminada
  { path: 'home', component: HomeComponent }, // Ruta para el componente "home"
  { path: '**', redirectTo: '' } // Redirigir rutas no válidas al login
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
