import { FilterOption } from 'src/app/core/types/filter-option.type';
import { SortingOption } from 'src/app/core/types/sorting-option.type';

export interface SearchOptions {
  term: string;
  filterOptions: FilterOption[];
  sortingOption: SortingOption;
}
