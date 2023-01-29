import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { SearchOptions } from 'src/app/shared/types/search-options.type';
import { environment } from 'src/environments/environment';
import { Collection } from '../types/collection.type';

@Injectable({
  providedIn: 'root',
})
export class CollectionsService {
  offset = 0;
  limit = 100;

  constructor(private httpClient: HttpClient) {}

  resetPagination() {
    this.offset = 0;
    this.limit = 100;
  }

  getCollections(searchOptions: SearchOptions): Observable<Collection[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;

    if (searchOptions.filterOptions?.length > 0) {
      queryString = this.updateQueryStringWithSearchOptions(
        searchOptions,
        queryString
      );
    }

    const url = `${environment.apiUrl}/collections/search?${queryString}`;

    return this.httpClient
      .get<Collection[]>(url)
      .pipe(tap(() => (this.offset += this.limit)));
  }

  getCollection(id: string): Observable<Collection> {
    return this.httpClient.get<Collection>(
      `${environment.apiUrl}/collections/${id}`
    );
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
