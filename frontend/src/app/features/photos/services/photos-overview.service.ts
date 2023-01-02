import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { PhotoView } from '../types';
import photoViews from './photo-views.json';

@Injectable({
  providedIn: 'root'
})
export class PhotosOverviewService {

  constructor() { }

  getPhotos(): Observable<PhotoView[]>{
    return of(photoViews);
  }
}
