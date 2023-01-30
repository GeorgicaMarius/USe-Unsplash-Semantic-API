import { Component, Input } from '@angular/core';
import { Photo } from '../../../../photos/types/photo.type';
import { Collection } from '../../../types/collection.type';
import { PhotosService } from '../../../../photos/services/photos.service';
import { first } from 'rxjs';

@Component({
  selector: 'app-photo-stack',
  templateUrl: './photo-stack.component.html',
  styleUrls: ['./photo-stack.component.scss'],
})
export class PhotoStackComponent {
  @Input() collection!: Collection;
  @Input() shuffleOnClick: boolean = true;

  photos: Photo[] = [];
  maxImageDimension = { width: 0, height: 0 };

  constructor(private readonly photosService: PhotosService) {}

  ngAfterViewInit() {
    // get the dimensions of the first image
    this.photosService
      .getPhotosById(this.collection.photoIds)
      .pipe(first())
      .subscribe((photos) => {
        photos.forEach((photo) => {
          photo.photoImageUrl = photo.photoImageUrl + '?w=300';
        });
        this.photos = photos.slice(0, 10);
        this.find();
      });
  }

  private find() {
    const img = new Image();
    // @ts-ignore
    img.src = this.photos[0].photoImageUrl;
    img.onload = () => {
      this.maxImageDimension = {
        width: Math.max(img.width, this.maxImageDimension.width),
        height: Math.max(img.height, this.maxImageDimension.height),
      };
    };
  }

  shiftArrayToRight(arr: Photo[], places: number) {
    for (var i = 0; i < places; i++) {
      // @ts-ignore
      arr.unshift(arr.pop());
    }
  }

  onClickImage() {
    if (this.shuffleOnClick) {
      this.shiftArrayToRight(this.photos, 1);
      return;
    }
  }
}
