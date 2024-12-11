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
  registerForm: FormGroup;
  opcionesIdentificacion: any[] = [];
  opcionesProfesion: any[] = [];
  opcionesTipoTrabajo: any[] = [];
  opcionesEstadoCivil: any[] = [];
  opcionesNivelEducativo: any[] = [];
  opcionesGenero: any[] = [];
  opcionesPaises: any[] = [];
  opcionesDepartamentos: any[] = [];
  opcionesCiudades: any[] = [];
  opcionesSedes: any[] = [];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      nombres: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      numeroIdentificacion: ['', Validators.required],
      telefono: ['', Validators.required],
      direccion: ['', Validators.required],
      ingresos: ['', Validators.required],
      egresos: ['', Validators.required],
      identificacion: ['', Validators.required],
      profesion: ['', Validators.required],
      tipoTrabajo: ['', Validators.required],
      estadoCivil: ['', Validators.required],
      nivelEducativo: ['', Validators.required],
      genero: ['', Validators.required],
      pais: ['', Validators.required],
      departamento: ['', Validators.required],
      ciudadResidencia: ['', Validators.required],
      sede: ['', Validators.required], // Campo para sedeId
    });
    
  }

  ngOnInit(): void {
    this.cargarValoresIniciales();
  }

  cargarValoresIniciales() {
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
    if (this.registerForm.valid) {
      const formData = this.registerForm.value;
  
      const payload = {
        nombres: formData.nombres,
        correo: formData.correo,
        password: formData.password,
        numeroIdentificacion: formData.numeroIdentificacion,
        telefono: formData.telefono,
        direccion: formData.direccion,
        ingresos: formData.ingresos,
        egresos: formData.egresos,
        identificacionId: formData.identificacion, // Solo el ID
        profesionId: formData.profesion, // Solo el ID
        tipoTrabajoId: formData.tipoTrabajo, // Solo el ID
        estadoCivilId: formData.estadoCivil, // Solo el ID
        nivelEducativoId: formData.nivelEducativo, // Solo el ID
        generoId: formData.genero, // Solo el ID
        ciudadResidenciaId: formData.ciudadResidencia, // Solo el ID
        habilitado: true,
        sedeId: formData.sede, // ID de la sede
      };
  
      console.log('Payload:', payload); // Debug
  
      this.http.post('http://localhost:8080/api/v1/usuarios', payload, { responseType: 'text' }).subscribe({
        next: (response) => {
          console.log('Respuesta del backend:', response);
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
