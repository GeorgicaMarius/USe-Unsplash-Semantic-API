import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Photo } from '../types/photo.type';

@Injectable({
  providedIn: 'root',
})
export class PhotosService {
  offset = 0;
  limit = 100;

  constructor(private httpClient: HttpClient) {}

  reset() {
    this.offset = 0;
    this.limit = 100;
  }

  getPhotos(searchTerm: string = ''): Observable<Photo[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;

    if (searchTerm) {
      queryString = queryString.concat(`&masterKeyword=${searchTerm}`);
    }

    const url = `${environment.apiUrl}/photos/search?${queryString}`;
    console.log(url)
    return this.httpClient
      .get<Photo[]>(url)
      .pipe(tap(() => (this.offset += this.limit)));
  }

  getPhoto(id: string): Observable<Photo> {
    return this.httpClient.get<Photo>(`${environment.apiUrl}/photos/${id}`);
  }
}
