import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Photo } from '../../../types/photo.type';

@Component({
  selector: 'app-photo-card',
  templateUrl: './photo-card.component.html',
  styleUrls: ['./photo-card.component.scss'],
})
export class PhotoCardComponent {
  @Input() photo!: Photo;

  errorOnImageLoading = false;

  onErrorLoadImage(): void {
    this.errorOnImageLoading = true;
  }
}
