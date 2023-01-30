import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { SearchService } from 'src/app/core/services/search.service';
import { FilterOption } from 'src/app/core/types/filter-option.type';
import { SearchOptions } from '../../types/search-options.type';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit {
  @Output() searchTermChange = new EventEmitter<SearchOptions>();
  isSearchBarValid = true;

  filterOptions: FilterOption[] = [];
  searchBarPlaceholder: string = 'Search...';
  searchTerm: string = '';

  constructor(private searchService: SearchService) {}

  ngOnInit(): void {
    this.subscribeToFilterOptions();
  }

  onSearchSubmit(): void {
    if (!this.searchTerm) {
      return;
    }

    this.searchTermChange.emit({
      term: this.searchTerm,
      filterOptions: this.filterOptions.filter((option) => option.checked),
    });
  }

  private subscribeToFilterOptions(): void {
    this.searchService.getObservable().subscribe((filterOption) => {
      this.updateFilterOptions(filterOption);
      this.updateSearchBarPlaceholder();
    });
  }

  updateFilterOptions(filterOption: FilterOption): void {
    if (filterOption.checked) {
      this.filterOptions.push(filterOption);
      return;
    }

    this.filterOptions = this.filterOptions.filter(
      (element) => element.option !== filterOption.option
    );
  }

  updateSearchBarPlaceholder(): void {
    this.searchBarPlaceholder = this.filterOptions.length
      ? 'Search by ' +
        this.filterOptions.map((element) => element.displayText).join(', ')
      : 'Search...';
  }
}
