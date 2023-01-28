import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Photo } from '../types/photo.type';

@Injectable({
  providedIn: 'root',
})
export class PhotosOverviewService {
  offset = 0;
  limit = 100;

  constructor(private httpClient: HttpClient) {}

  getPhotos(offset: number = 0, limit: number = 0): Observable<Photo[]> {
    const queryString = offset && limit
        ? `offset=${offset}&limit=${limit}`
        : `offset=${this.offset}&limit=${this.limit}`;

    return this.httpClient.get<Photo[]>(
      `http://localhost:8080/photos?${queryString}`
    ).pipe(
      tap(() => this.offset += this.limit)
    );
  }
}
