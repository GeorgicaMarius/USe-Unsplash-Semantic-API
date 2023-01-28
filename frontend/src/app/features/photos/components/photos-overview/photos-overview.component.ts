import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { first } from 'rxjs';
import { ScrollWatchDirective } from 'src/app/shared/directives/scroll-watch.directive';
import { PhotosOverviewService } from '../../services/photos-overview.service';
import { Photo } from '../../types/photo.type';

@Component({
  selector: 'app-photos-overview',
  templateUrl: './photos-overview.component.html',
  styleUrls: ['./photos-overview.component.scss'],
})
export class PhotosOverviewComponent implements OnInit {
  title = 'Photos';
  displayOverview = true;
  numberOfColumnsToDisplay = 3;
  indexOfLastLoadedPhoto = 0;
  maxNumberOfDisplayedPhotosPerLoad = 30;

  lastScrolledPosition = 0;

  photos: Photo[] = [];
  displayedPhotos: Photo[][] = [];
  currentViewingPhoto: Photo = {} as Photo;

  isLoading = false;

  constructor(
    private readonly photosOverviewService: PhotosOverviewService,
    private loaderService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.initializeColumns();
    this.getPhotos();
  }

  onOpenedDetails(photo: Photo): void {
    this.displayOverview = false;
    this.currentViewingPhoto = photo;
  }

  onDetailsClickedBackButton(): void {
    this.displayOverview = true;
  }

  getArray(mapValues: any): Photo[] {
    return [...mapValues];
  }

  updateDisplayedPhotos(): void {
    this.getPhotosToDisplayForCurrentPage();
  }

  private initializeColumns() {
    for (
      let columnIndex = 0;
      columnIndex < this.numberOfColumnsToDisplay;
      columnIndex++
    ) {
      this.displayedPhotos.push([]);
    }
  }

  private getPhotos(): void {
    this.photosOverviewService
      .getPhotos()
      .pipe(first())
      .subscribe((photos) => {
        this.photos.push(...photos);
        this.updateDisplayedPhotos();
      });
  }

  private getPhotosToDisplayForCurrentPage(): void {
    this.updatePhotoColumns();

    const remaingPhotosNumberLessThanLoadSize =
      this.photos.length <
      this.indexOfLastLoadedPhoto + this.maxNumberOfDisplayedPhotosPerLoad;

    if (remaingPhotosNumberLessThanLoadSize) {
      this.indexOfLastLoadedPhoto = this.photos.length;
      return;
    }

    this.indexOfLastLoadedPhoto += this.maxNumberOfDisplayedPhotosPerLoad;

    if (this.shouldRetrieveMorePhotos()) {
      this.getPhotos();
    }
  }

  private updatePhotoColumns(): void {
    for (
      let columnIndex = 0;
      columnIndex < this.numberOfColumnsToDisplay;
      columnIndex++
    ) {
      this.updateColumn(columnIndex);
    }
  }

  private updateColumn(columnIndex: number) {
    let columnPhotos: Photo[] = [];
    const startIndex = this.indexOfLastLoadedPhoto + columnIndex;
    const endIndex =
      this.indexOfLastLoadedPhoto + this.maxNumberOfDisplayedPhotosPerLoad;

    for (
      let index = startIndex;
      index < endIndex;
      index += this.numberOfColumnsToDisplay
    ) {
      columnPhotos.push(this.photos[index]);
    }

    this.displayedPhotos[columnIndex].push(...columnPhotos);
  }

  private shouldRetrieveMorePhotos(): boolean {
    return (
      this.photos.length - this.indexOfLastLoadedPhoto <
      this.maxNumberOfDisplayedPhotosPerLoad
    );
  }

}
