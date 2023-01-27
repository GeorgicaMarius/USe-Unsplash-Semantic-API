import { Component, Input } from '@angular/core';
import { Photo } from '../../../types/photo-response.type';

@Component({
  selector: 'app-photo-card',
  templateUrl: './photo-card.component.html',
  styleUrls: ['./photo-card.component.scss'],
})
export class PhotoCardComponent {
  @Input()
  photo!: Photo;
}
