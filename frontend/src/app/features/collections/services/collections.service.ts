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

  getCollections(term: string): Observable<Collection[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;

    if (term) {
      queryString += `&title=${term}`;
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
}
