import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Photo } from '../../../types/photo.type';

@Component({
  selector: 'app-photo-details',
  templateUrl: './photo-details.component.html',
  styleUrls: ['./photo-details.component.scss'],
})
export class PhotoDetailsComponent {
  @Input() photo!: Photo;
}
