import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { SearchService } from 'src/app/core/services/search.service';

@Component({
  selector: 'app-filter-menu',
  templateUrl: './filter-menu.component.html',
  styleUrls: ['./filter-menu.component.scss'],
})
export class FilterMenuComponent {
  displayPhotoFilter: boolean = true;
  displayFilterMenu: boolean = true;

  constructor(private searchService: SearchService, private router: Router) {
    this.subscribeToRouterEvents();
  }

  subscribeToRouterEvents(): void {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const segments = this.router.url.split('/');
        const text = segments.pop();
        this.displayFilterMenu = text === 'photos';
      });
  }

  onChangeCheckbox(value: any, filterOption: string, displayText: string) {
    this.searchService.addFilterOption({
      checked: value.checked,
      option: filterOption,
      displayText: displayText,
    });
  }
}
