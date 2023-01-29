import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-routing-menu',
  templateUrl: './routing-menu.component.html',
  styleUrls: ['./routing-menu.component.scss'],
})
export class RoutingMenuComponent {
  constructor(private router: Router) {}

  navigateToCollections(): void {
    this.router.navigateByUrl('/collections');
  }

  navigateToPhotos(): void {
    this.router.navigateByUrl('/photos');
  }
}
