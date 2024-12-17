import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  isExpanded: boolean = true;

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }
}
