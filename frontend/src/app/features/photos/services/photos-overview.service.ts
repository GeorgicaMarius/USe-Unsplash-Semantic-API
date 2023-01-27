import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Photo } from '../types/photo-response.type';

@Injectable({
  providedIn: 'root',
})
export class PhotosOverviewService {
  constructor(private httpClient: HttpClient) {}

  getPhotos(offset: number = 0, limit: number = 100): Observable<Photo[]> {
    const queryString = `offset=${offset}&limit=${limit}`;
    return this.httpClient.get<Photo[]>(
      `http://localhost:8080/photos?${queryString}`
    );
  }
}
