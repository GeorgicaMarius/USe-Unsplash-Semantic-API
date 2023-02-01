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
  displayFilterMenu: boolean = true;

  constructor(private searchService: SearchService, private router: Router) {}

  onChangeCheckbox(value: any, filterOption: string, displayText: string) {
    this.searchService.addFilterOption({
      checked: value.checked,
      option: filterOption,
      displayText: displayText,
    });
  }
}
