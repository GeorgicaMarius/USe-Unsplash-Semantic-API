import { FilterOption } from 'src/app/core/types/filter-option.type';

export interface SearchOptions {
  term: string;
  filterOptions: FilterOption[];
}
