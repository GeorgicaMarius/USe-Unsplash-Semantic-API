import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs';
import {PhotoAdditionalInfoService} from '../../../services/photo-additional-info.service';
import {PhotosService} from '../../../services/photos.service';
import {Photo} from '../../../types/photo.type';
import {ImageAdjustmentComponent} from './image-adjustment/image-adjustment-component';
import {MatDialog} from '@angular/material/dialog';
import {AugmentParameters} from '../../../types/augment-parameters';
import {ViewportRuler} from '@angular/cdk/overlay';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-photo-details',
  templateUrl: './photo-details.component.html',
  styleUrls: ['./photo-details.component.scss'],
})
export class PhotoDetailsComponent {
  id: string = '';
  collectionId: string = '';
  photo!: Photo;
  additionalData: Map<string, any> = new Map<string, any>();

  isLoading = true;

  augmentParameters: AugmentParameters = {
    brightness: 0,
    contrast: 0,
    saturation: 0,
    shadow: 0,
    highlight: 0,
    sharpness: 0,
  };
  augmentParametersString: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private photoService: PhotosService,
    private additionalInfoService: PhotoAdditionalInfoService,
    private dialog: MatDialog,
    private viewportRuler: ViewportRuler,
    private http: HttpClient
  ) {
    this.getIdFromUrl();
  }

  openImageAdjustment(): void {
    const width =
      this.viewportRuler.getViewportSize().width < 768 ? '100%' : '50%';

    const dialogRef = this.dialog.open(ImageAdjustmentComponent, {
      width: width,
      data: {augmentParameters: this.augmentParameters},
    });

    dialogRef.afterClosed().subscribe((elem) => {
      if (elem) {
        this.augmentParameters = elem;
        this.augmentParametersString = `&bri=${this.augmentParameters.brightness}&con=${this.augmentParameters.contrast}&sat=${this.augmentParameters.saturation}&shad=${this.augmentParameters.shadow}&high=${this.augmentParameters.highlight}&sharp=${this.augmentParameters.sharpness}`;
      }
    });
  }

  getIdFromUrl(): void {
    this.activatedRoute.paramMap.pipe(first()).subscribe((map) => {
      const routeId = map.get('id');
      this.id = routeId ?? '';

      const routePhotoId = map.get('photoId');
      if (routePhotoId) {
        this.id = routePhotoId;
        this.collectionId = map.get('collectionId') ?? '';
      }

      this.getInformation();
    });
  }

  getInformation(): void {
    this.photoService
      .getPhoto(this.id)
      .pipe(first())
      .subscribe((photo) => {
        this.photo = photo;
        this.getAdditionalInformation();
      });
  }

  getAdditionalInformation(): void {
    this.getAdditionalCityInfo();
    this.getAdditionalCountryInfo();
    this.isLoading = false;
  }

  getAdditionalCityInfo(): void {
    if (!this.photo.photoLocationCity) {
      return;
    }

    this.additionalInfoService
      .getCityInformation(this.photo.photoLocationCity)
      .pipe(first())
      .subscribe((cityInfo) => this.additionalData.set('city', cityInfo));
  }

  getAdditionalCountryInfo(): void {
    if (!this.photo.photoLocationCountry) {
      return;
    }

    this.additionalInfoService
      .getCountryInformation(this.photo.photoLocationCountry)
      .pipe(first())
      .subscribe((countryInfo) => {
        this.additionalData.set('country', countryInfo);
      });
  }

  onClickedBackButton(): void {
    if (this.collectionId) {
      this.router.navigateByUrl(`/collections/${this.collectionId}`);
      return;
    }

    this.router.navigateByUrl(this.getPhotosOverviewPath());
  }

  private getPhotosOverviewPath(): string {
    let index = this.router.url.lastIndexOf('/');
    index = index == -1 ? this.router.url.length : index;
    return this.router.url.substring(0, index);
  }

  downloadImage() {
    const link = document.createElement('a');
    link.href = this.photo.photoImageUrl + '?' + this.augmentParametersString;
    link.download = 'image.jpg';

    this.http.get(link.href, { responseType: 'blob' }).subscribe(response => {
      link.href = URL.createObjectURL(response);
      link.click();

      setTimeout(() => {
        URL.revokeObjectURL(link.href);
      }, 100);
    });
  }
}
