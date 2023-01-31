import { Component, Input } from '@angular/core';
import { Photo } from '../../../../photos/types/photo.type';
import { Collection } from '../../../types/collection.type';
import { PhotosService } from '../../../../photos/services/photos.service';
import { first } from 'rxjs';
import { Router } from '@angular/router';

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
  sortedPhotos : [] = [];

  constructor(
    private readonly photosService: PhotosService,
    private readonly router: Router
  ) {}

  ngAfterViewInit() {
    // get the dimensions of the first image
    this.photosService
      .getPhotosById(this.collection.photoIds)
      .pipe(first())
      .subscribe((photos) => {
        this.photos = photos.slice(0, 10);
        photos.forEach((photo) => {
          photo.photoImageUrl = photo.photoImageUrl + '?w=300';
          this.find(photo);
        });
      });
  }

  private find(photo: Photo) {
    const img = new Image();
    // @ts-ignore
    img.src = photo.photoImageUrl;
    img.onload = () => {
      // @ts-ignore
      this.sortedPhotos.push([img.height, photo])
      this.maxImageDimension = {
        width: Math.max(img.width, this.maxImageDimension.width),
        height: Math.max(img.height, this.maxImageDimension.height),
      };
      if (this.sortedPhotos.length === this.photos.length) {
        this.sortedPhotos.sort((a, b) => a[0] - b[0]);
        this.photos = this.sortedPhotos.map(pair => pair[1])
      }
    };
  }

  shiftArrayToRight(arr: Photo[], places: number) {
    for (var i = 0; i < places; i++) {
      // @ts-ignore
      arr.unshift(arr.pop());
    }
  }

  shiftArrayToLeft(arr: Photo[], places: number) {
    for (var i = 0; i < places; i++) {
      // @ts-ignore
      arr.push(arr.shift());
    }
  }

  onClickImage() {
    if (this.shuffleOnClick && this.photos.length > 1) {
      this.shiftArrayToLeft(this.photos, 1);
      return;
    }

    this.router.navigateByUrl(
      `${this.router.url}/${this.collection.collectionId}`
    );
  }
}
