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
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { SidebarComponent } from './sidebar/sidebar.component';
import { LayoutComponent } from './layout/layout.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    SidebarComponent,
    LayoutComponent,
    NavbarComponent,
    FooterComponent
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
    MatCardModule, // Tarjetas para diseño visual
    MatSelectModule,
    MatStepperModule,
    MatSidenavModule, // Sidenav para la barra lateral
    MatListModule    // Listas dentro del sidenav
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
