import {Component, Input} from '@angular/core';
import {Photo} from "../../../../photos/types/photo.type";
import {Collection} from "../../../types/collection.type";
import {PhotosService} from "../../../../photos/services/photos.service";
import {first} from "rxjs";

@Component({
  selector: 'app-photo-stack',
  template: `
    <div *ngFor="let photo of photos; let i = index" [ngStyle]="{'z-index': photos.length - i}">
      <img class="first-image" *ngIf="i === 0" [src]="photo.photoImageUrl" [ngStyle]="{'width': 'auto', 'height': 'auto'}">
      <img *ngIf="i !== 0" [src]="photo.photoImageUrl" [ngStyle]="{'width': firstImageDimensions.width + 'px', 'height': firstImageDimensions.height + 'px', 'transform': 'rotate('+i*5+'deg)'}">
    </div>
  `,
  styles: [`
    img {
      position: absolute;
      margin: 10px;
      transition: transform 0.3s ease-in-out;
    }

    div {
      position: relative;
    }
  `]
})
export class PhotoStackComponent {
  @Input() collection!: Collection;
  photos: Photo[] = [];
  firstImageDimensions = {width: 0, height: 0};

  constructor(private readonly photosService: PhotosService) {
  }

  ngAfterViewInit() {
    // get the dimensions of the first image
    this.photosService
      .getPhotosById(this.collection.photoIds)
      .pipe(first())
      .subscribe((photos) => {
        photos.forEach(photo => {
          photo.photoImageUrl = photo.photoImageUrl + '?w=300'
        });
        this.photos = photos.slice(0,10);
        const img = new Image();
        // @ts-ignore
        img.src = this.photos[0].photoImageUrl;
        img.onload = () => {
          this.firstImageDimensions = {width: img.width, height: img.height};
        };
      });
  }
}
