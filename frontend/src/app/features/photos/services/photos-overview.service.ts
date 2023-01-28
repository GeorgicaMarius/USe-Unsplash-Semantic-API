import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, tap} from 'rxjs';
import {Photo} from '../types/photo.type';

@Injectable({
  providedIn: 'root',
})
export class PhotosOverviewService {
  offset = 0;
  limit = 100;

  constructor(private httpClient: HttpClient) {
  }

  reset() {
    this.offset = 0;
    this.limit = 100;
  }

  getPhotos(searchTerm: string = ''): Observable<Photo[]> {
    let queryString = `offset=${this.offset}&limit=${this.limit}`;
    if (searchTerm != '') {
      queryString = queryString.concat(`&masterKeyword=${searchTerm}`);
    }
    console.log(`http://localhost:8080/photos/search?${queryString}`)

    return this.httpClient.get<Photo[]>(
      `http://localhost:8080/photos/search?${queryString}`
    ).pipe(
      tap(() => this.offset += this.limit)
    );
  }
}
