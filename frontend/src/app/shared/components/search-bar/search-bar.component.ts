import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SearchService } from 'src/app/core/services/search.service';
import { FilterOption } from 'src/app/core/types/filter-option.type';
import { SortingOption } from 'src/app/core/types/sorting-option.type';
import { SearchOptions } from '../../types/search-options.type';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit {
  @Output() searchTermChange = new EventEmitter<SearchOptions>();
  isSearchBarValid = true;

  sortingOption: SortingOption = {
    isAscending: true,
    option: '',
  };

  filterOptions: FilterOption[] = [];
  searchBarPlaceholder: string = 'Search...';
  searchTerm: string = '';

  constructor(private searchService: SearchService) {}

  ngOnInit(): void {
    this.subscribeToFilterOptions();
  }

  onSearchSubmit(): void {
    if (!this.searchTerm && !this.sortingOption.option) {
      return;
    }

    this.searchTermChange.emit({
      term: this.searchTerm,
      filterOptions: this.filterOptions.filter((option) => option.checked),
      sortingOption: this.sortingOption,
    });
  }

  private subscribeToFilterOptions(): void {
    this.searchService.searchModifier$.subscribe((searchModifiers) => {
      if (searchModifiers.filterOption) {
        this.updateFilterOptions(searchModifiers.filterOption);
      }

      if (searchModifiers.sortingOption) {
        this.updateSortingOption(searchModifiers.sortingOption);
      }

      this.updateSearchBarPlaceholder();
    });
  }

  updateFilterOptions(filterOption: FilterOption): void {
    if (filterOption.checked) {
      const isOptionAlreadySelected =
        this.filterOptions.filter(
          (element) => element.option === filterOption.option
        ).length > 0;

      if (!isOptionAlreadySelected) {
        this.filterOptions.push(filterOption);
      }
    
      return;
    }

    this.filterOptions = this.filterOptions.filter(
      (element) => element.option !== filterOption.option
    );
  }

  updateSortingOption(sortingOption: SortingOption): void {
    this.sortingOption = sortingOption;
  }

  updateSearchBarPlaceholder(): void {
    this.searchBarPlaceholder = this.filterOptions.length
      ? 'Search by ' +
        this.filterOptions.map((element) => element.displayText).join(', ')
      : 'Search...';
  }
}
