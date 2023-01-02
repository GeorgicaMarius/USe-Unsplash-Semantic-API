import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs';
import { PhotosOverviewService } from '../../services/photos-overview.service';
import { PhotoView } from '../../types';

@Component({
  selector: 'app-photos-overview',
  templateUrl: './photos-overview.component.html',
  styleUrls: ['./photos-overview.component.scss']
})
export class PhotosOverviewComponent implements OnInit {

  photos: PhotoView[] = [];

  constructor(private readonly photosOverviewService: PhotosOverviewService) {}

  ngOnInit(): void {
    this.getPhotos();
  }

  private getPhotos(): void{
    this.photosOverviewService.getPhotos().pipe(first()).subscribe(photos => {
      this.photos = photos;
    })
  }

}
