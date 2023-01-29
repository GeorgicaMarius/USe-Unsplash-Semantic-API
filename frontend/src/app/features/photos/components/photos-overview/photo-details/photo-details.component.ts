import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs';
import { PhotoAdditionalInfoService } from '../../../services/photo-additional-info.service';
import { PhotosService } from '../../../services/photos.service';
import { Photo } from '../../../types/photo.type';

@Component({
  selector: 'app-photo-details',
  templateUrl: './photo-details.component.html',
  styleUrls: ['./photo-details.component.scss'],
})
export class PhotoDetailsComponent {
  id: string = '';
  photo!: Photo;
  additionalData: Map<string, any> = new Map<string, any>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private photoService: PhotosService,
    private additionalInfoService: PhotoAdditionalInfoService
  ) {
    this.getIdFromUrl();
  }

  getIdFromUrl(): void {
    this.activatedRoute.paramMap.pipe(first()).subscribe((map) => {
      this.id = map.get('id') ?? '';
      this.getInformation();
    });
  }

  private getInformation(): void {
    this.photoService
      .getPhoto(this.id)
      .pipe(first())
      .subscribe((photo) => {
        this.photo = photo;
        this.getAdditionalInformation();
      });
  }

  getAdditionalInformation(): void {
    this.additionalInfoService
      .getCityInformation(this.photo.photoLocationCity)
      .pipe(first())
      .subscribe((cityInfo) => this.additionalData.set('city', cityInfo));

    this.additionalInfoService
      .getCountryInformation(this.photo.photoLocationCountry)
      .pipe(first())
      .subscribe((countryInfo) =>
        this.additionalData.set('country', countryInfo)
      );
  }
}
