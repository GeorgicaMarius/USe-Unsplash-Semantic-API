import {Component, Input} from '@angular/core';
import {Photo} from "../../../../photos/types/photo.type";
import {Collection} from "../../../types/collection.type";
import {PhotosService} from "../../../../photos/services/photos.service";
import {first} from "rxjs";

@Component({
  selector: 'app-photo-stack',
  template: `
    <div
      [ngStyle]="{'width' :  (maxImageDimension.width + 100)+ 'px', 'height' : (maxImageDimension.height+ 100) + 'px', 'overflow': 'hidden'}">
      <div *ngFor="let photo of photos; let i = index" [ngStyle]="{'z-index': photos.length - i}">
        <img (click)="changeImage()" [src]="photo.photoImageUrl"
             [ngStyle]="{'transform': 'rotate('+i*5+'deg)', 'margin': '0', 'box-shadow': i === 0 ? '0 0 10px 2px #3B8CE0' : '', 'top': i === 0? '10px': '', 'left': i===0?'10px':''}">
      </div>
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
  maxImageDimension = {width: 0, height: 0};

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
        height: Math.max(img.height, this.maxImageDimension.height)
      };
    };
  }

  shiftArrayToRight(arr: Photo[], places: number) {
    for (var i = 0; i < places; i++) {
      // @ts-ignore
      arr.unshift(arr.pop());
    }
  }

  changeImage() {
    this.shiftArrayToRight(this.photos, 1)
  }
}
