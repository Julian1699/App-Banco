import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  personalFormGroup!: FormGroup; // Información Personal
  addressFormGroup!: FormGroup; // Dirección
  additionalFormGroup!: FormGroup; // Información Adicional
  opcionesIdentificacion: any[] = [];
  opcionesPaises: any[] = [];
  opcionesDepartamentos: any[] = [];
  opcionesCiudades: any[] = [];
  opcionesSedes: any[] = [];
  opcionesProfesion: any[] = [];
  opcionesTipoTrabajo: any[] = [];
  opcionesEstadoCivil: any[] = [];
  opcionesNivelEducativo: any[] = [];
  opcionesGenero: any[] = [];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}
  initializeForms() {
    this.personalFormGroup = this.fb.group({
      identificacionId: ['', Validators.required],
      numeroIdentificacion: ['', Validators.required],
      nombres: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      telefono: ['', Validators.required],
    });
  
    this.addressFormGroup = this.fb.group({
      direccion: ['', Validators.required],
      pais: ['', Validators.required],
      departamento: ['', Validators.required],
      ciudadResidenciaId: ['', Validators.required],
    });
  
    this.additionalFormGroup = this.fb.group({
      sedeId: ['', Validators.required],
      profesionId: ['', Validators.required],
      tipoTrabajoId: ['', Validators.required],
      estadoCivilId: ['', Validators.required],
      nivelEducativoId: ['', Validators.required],
      generoId: ['', Validators.required],
      ingresos: ['', [Validators.required, Validators.min(0)]],
      egresos: ['', [Validators.required, Validators.min(0)]],
      habilitado: [true]
    });
  }

  ngOnInit(): void {
    this.initializeForms();
    this.loadDropdownData();
  }

  loadDropdownData() {
    this.http.get('http://localhost:8080/api/v1/register/listas/2/valores').subscribe((data: any) => (this.opcionesIdentificacion = data));
    this.http.get('http://localhost:8080/api/v1/register/listas/4/valores').subscribe((data: any) => (this.opcionesProfesion = data));
    this.http.get('http://localhost:8080/api/v1/register/listas/3/valores').subscribe((data: any) => (this.opcionesTipoTrabajo = data));
    this.http.get('http://localhost:8080/api/v1/register/listas/5/valores').subscribe((data: any) => (this.opcionesEstadoCivil = data));
    this.http.get('http://localhost:8080/api/v1/register/listas/6/valores').subscribe((data: any) => (this.opcionesNivelEducativo = data));
    this.http.get('http://localhost:8080/api/v1/register/listas/7/valores').subscribe((data: any) => (this.opcionesGenero = data));
    this.http.get('http://localhost:8080/api/v1/register/paises').subscribe((data: any) => (this.opcionesPaises = data));
  }

  onPaisChange(paisId: number) {
    this.http
      .get(`http://localhost:8080/api/v1/register/paises/${paisId}/departamentos`)
      .subscribe((data: any) => (this.opcionesDepartamentos = data));
  }

  onDepartamentoChange(departamentoId: number) {
    this.http
      .get(`http://localhost:8080/api/v1/register/departamentos/${departamentoId}/ciudades`)
      .subscribe((data: any) => (this.opcionesCiudades = data));
  }

  onCiudadChange(ciudadId: number) {
    this.http
      .get(`http://localhost:8080/api/v1/register/ciudades/${ciudadId}/sedes`)
      .subscribe((data: any) => (this.opcionesSedes = data));
  }

  onRegister() {
    if (this.personalFormGroup.valid && this.addressFormGroup.valid && this.additionalFormGroup.valid) {
      const payload = {
        ...this.personalFormGroup.value,
        ...this.addressFormGroup.value,
        ...this.additionalFormGroup.value,
      };
  
      this.http.post('http://localhost:8080/api/v1/usuarios', payload, { responseType: 'text' }).subscribe({
        next: (response) => {
          Swal.fire({
            icon: 'success',
            title: '¡Registro exitoso!',
            text: response, // Mostrar el mensaje de texto plano
            confirmButtonColor: '#4CAF50',
          }).then(() => {
            this.router.navigate(['/']); // Redirige al login
          });
        },
        error: (err) => {
          console.error('Error en el registro:', err);
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: err.error.message || 'Ocurrió un problema al registrar al usuario.',
            confirmButtonColor: '#FF5733',
          });
        },
      });
    } else {
      Swal.fire({
        icon: 'warning',
        title: 'Formulario incompleto',
        text: 'Por favor, completa todos los campos obligatorios.',
        confirmButtonColor: '#FFC107',
      });
    }
  } 

}
