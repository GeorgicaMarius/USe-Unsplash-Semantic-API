import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { FilterOption } from '../types/filter-option.type';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  $checkedFilterOptions: Subject<FilterOption> = new Subject();

  getObservable(): Observable<FilterOption> {
    return this.$checkedFilterOptions.asObservable();
  }

  addFilterOption(filterOption: FilterOption): void {
    this.$checkedFilterOptions.next(filterOption);
  }
}
