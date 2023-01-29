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

  getPhotos(searchOptions?: SearchOptions): Observable<Photo[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;

    if (searchOptions) {
      queryString = this.updateQueryStringWithSearchOptions(
        searchOptions,
        queryString
      );
    }

    const url = `${environment.apiUrl}/photos/search?${queryString}`;

    return this.httpClient
      .get<Photo[]>(url)
      .pipe(tap(() => (this.offset += this.limit)));
  }

  getPhoto(id: string): Observable<Photo> {
    return this.httpClient.get<Photo>(`${environment.apiUrl}/photos/${id}`);
  }

  private updateQueryStringWithSearchOptions(
    searchOptions: SearchOptions,
    queryString: string
  ): string {
    if (searchOptions.filterOptions.length) {
      const searchValues = searchOptions.term.split(',');
      const queryStringFilterOptions = searchOptions.filterOptions.map(
        (element, index) => `${element.option}=${searchValues[index].trim()}`
      );

      queryString += `&${queryStringFilterOptions.join('&')}`;
      return queryString;
    }

    return (queryString = queryString.concat(
      `&masterKeyword=${searchOptions!.term}`
    ));
  }
}
