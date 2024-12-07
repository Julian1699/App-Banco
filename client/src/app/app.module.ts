import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/* Importar módulos de Angular Material */
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule, // Para solicitudes HTTP
    FormsModule, // Formularios simples
    ReactiveFormsModule, // Formularios reactivos
    MatFormFieldModule, // Campo de formulario de Angular Material
    MatInputModule, // Campos de texto
    MatButtonModule, // Botones
    MatIconModule, // Iconos de Material Design
    MatCardModule // Tarjetas para diseño visual
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
