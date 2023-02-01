import { Injectable } from '@angular/core';
import { combineLatest, map, Observable, startWith, Subject } from 'rxjs';
import { FilterOption } from '../types/filter-option.type';
import { SortingOption } from '../types/sorting-option.type';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  checkedFilterOptions$: Subject<FilterOption> = new Subject();
  sortingOption$: Subject<SortingOption> = new Subject();

  searchModifier$ = combineLatest([
    this.checkedFilterOptions$.pipe(startWith(null)),
    this.sortingOption$.pipe(startWith({ isAscending: true, option: ''})),
  ]).pipe(
    map(
      ([filterOption, sortingOption]) => { return {filterOption, sortingOption} }
    )
  );

  addFilterOption(filterOption: FilterOption): void {
    this.checkedFilterOptions$.next(filterOption);
  }

  addSortingOption(sortingOption: SortingOption): void {
    this.sortingOption$.next(sortingOption);
  }
}
