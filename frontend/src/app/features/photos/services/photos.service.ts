import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { FilterOption } from 'src/app/core/types/filter-option.type';
import { SearchOptions } from 'src/app/shared/types/search-options.type';
import { environment } from 'src/environments/environment';
import { Photo } from '../types/photo.type';

@Injectable({
  providedIn: 'root',
})
export class PhotosService {
  offset = 0;
  limit = 100;

  constructor(private httpClient: HttpClient) {}

  resetPagination() {
    this.offset = 0;
    this.limit = 100;
  }

  getPhotos(searchOptions: SearchOptions): Observable<Photo[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;

    queryString = this.addFilterOptions(searchOptions, queryString);
    queryString = this.addSortingOption(searchOptions, queryString);

    const url = `${environment.apiUrl}/photos/search?${queryString}`;

    return this.httpClient
      .get<Photo[]>(url)
      .pipe(tap(() => (this.offset += this.limit)));
  }

  getPhotosById(ids: string[]): Observable<Photo[]> {
    return this.httpClient.post<Photo[]>(`${environment.apiUrl}/photos`, ids);
  }

  getPhoto(id: string): Observable<Photo> {
    return this.httpClient.get<Photo>(`${environment.apiUrl}/photos/${id}`);
  }

  private addSortingOption(
    searchOptions: SearchOptions,
    queryString: string
  ): string {
    if (searchOptions.sortingOption?.option) {
      const sortingOption = searchOptions.sortingOption.option;
      const sortingOrder = searchOptions.sortingOption.isAscending
        ? 'asc'
        : 'desc';
      queryString = queryString.concat(
        `&orderBy=${sortingOption}_${sortingOrder}`
      );
    }

    return queryString;
  }

  private addFilterOptions(
    searchOptions: SearchOptions,
    queryString: string
  ): string {
    if (searchOptions.filterOptions?.length > 0) {
      return (queryString = this.updateQueryStringWithFilterOptions(
        searchOptions,
        queryString
      ));
    } else if (searchOptions.term) {
      return (queryString = queryString.concat(
        `&masterKeyword=${searchOptions.term}`
      ));
    }

    return queryString;
  }

  private updateQueryStringWithFilterOptions(
    searchOptions: SearchOptions,
    queryString: string
  ): string {
    const searchValues = searchOptions.term.split(',');
    const queryStringFilterOptions = searchOptions.filterOptions.map(
      (element, index) => `${element.option}=${searchValues[index].trim()}`
    );

    queryString += `&${queryStringFilterOptions.join('&')}`;
    return queryString;
  }
}
