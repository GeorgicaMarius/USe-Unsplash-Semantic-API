import { Component, Input } from '@angular/core';
import { Photo } from 'src/app/features/photos/types/photo.type';

@Component({
  selector: 'app-photo-info',
  templateUrl: './photo-info.component.html',
  styleUrls: ['./photo-info.component.scss'],
})
export class PhotoInfoComponent {
  resourceUrl: string;
  @Input() photo!: Photo;

  constructor() {
    this.resourceUrl = window.location.origin + window.location.pathname;
  }
}
