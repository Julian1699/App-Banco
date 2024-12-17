import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
})
export class LayoutComponent {
  isExpanded = true; // Estado de la sidebar

  toggleSidebar(): void {
    this.isExpanded = !this.isExpanded; // Alterna el estado de la sidebar
  }
}
