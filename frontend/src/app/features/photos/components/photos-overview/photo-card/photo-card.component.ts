import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { first } from 'rxjs';
import { Photo } from '../../../types/photo.type';

@Component({
  selector: 'app-photo-card',
  templateUrl: './photo-card.component.html',
  styleUrls: ['./photo-card.component.scss'],
})
export class PhotoCardComponent implements OnInit {
  @Input() photo!: Photo;

  url: string = '';

  errorOnImageLoading = false;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.url = `/photos/${this.photo.photoId}`;
    this.getRouteParams();
  }

  getRouteParams(): void {
    this.activatedRoute.paramMap.pipe(first()).subscribe((map) => {
      const routeCollectionId = map.get('id');
      if (routeCollectionId) {
        this.url = `/collections/${routeCollectionId}/${this.photo.photoId}`;
      }
    });
  }

  onErrorLoadImage(): void {
    this.errorOnImageLoading = true;
  }
}
