import { Component } from '@angular/core';
import { SearchService } from 'src/app/core/services/search.service';

@Component({
  selector: 'app-sort-menu',
  templateUrl: './sort-menu.component.html',
  styleUrls: ['./sort-menu.component.scss'],
})
export class SortMenuComponent {
  isAscending: boolean = false;
  selectedOption: string = '';

  options = [
    { value: 'downloads', viewValue: 'Downloads' },
    { value: 'views', viewValue: 'Views' },
    { value: 'submitted', viewValue: 'Submitted Date' },
    { value: 'width', viewValue: 'Width' },
    { value: 'height', viewValue: 'Height' },
    { value: 'city', viewValue: 'City' },
    { value: 'country', viewValue: 'Country' },
  ];

  constructor(private readonly searchService: SearchService) {}

  onCheckboxChange(checkbox: any): void {
    this.isAscending = checkbox.checked;

    this.searchService.addSortingOption({
      isAscending: this.isAscending,
      option: this.selectedOption,
    });
  }

  onSelectingCriteria(option: any): void {
    this.selectedOption = option.value ?? '';

    this.searchService.addSortingOption({
      isAscending: this.isAscending,
      option: option.value,
    });
  }
}
