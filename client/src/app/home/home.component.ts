import { Component, OnInit } from '@angular/core';
import { jwtDecode } from 'jwt-decode'; // Instalar con `npm install jwt-decode`

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  userName: string = '';

  ngOnInit() {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = this.decodeToken(token);
      this.userName = decodedToken?.nombre || 'Usuario'; // Muestra "Usuario" si el nombre no está en el token
    }
  }

  decodeToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (error) {
      console.error('Error decodificando el token:', error);
      return null;
    }
  }
}
