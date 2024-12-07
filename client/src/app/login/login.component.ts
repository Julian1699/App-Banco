import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const payload = { correo: this.email, password: this.password };

    this.http.post('http://localhost:8080/api/v1/auth/login', payload).subscribe({
      next: (response: any) => {
        console.log('Token recibido:', response.token);
        localStorage.setItem('token', response.token); // Almacenar el token

        // Mostrar modal de bienvenida
        Swal.fire({
          icon: 'success',
          title: '¡Bienvenido!',
          text: 'Inicio de sesión exitoso.',
          confirmButtonText: 'Continuar',
          confirmButtonColor: '#4CAF50'
        }).then(() => {
          this.router.navigate(['/home']); // Redirigir al dashboard
        });
      },
      error: (err) => {
        console.error('Error al iniciar sesión:', err);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: err.error.error || 'Credenciales incorrectas',
          confirmButtonColor: '#FF5733'
        });
      }
    });
  }
}
