import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent {
  isSideMenuOpen = false;
  displayFilterAndOrderMenu: boolean = true;

  constructor(private readonly router: Router) {
    this.subscribeToRouterEvents();
  }

  subscribeToRouterEvents(): void {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const segments = this.router.url.split('/');
        const text = segments.pop();
        this.displayFilterAndOrderMenu = text === 'photos';
      });
  }

  onClickMenuIcon(): void {
    this.isSideMenuOpen = !this.isSideMenuOpen;
  }
}
