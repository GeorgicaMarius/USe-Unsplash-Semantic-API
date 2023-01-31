import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { first } from 'rxjs';
import { SearchOptions } from 'src/app/shared/types/search-options.type';
import { PhotosService } from '../../services/photos.service';
import { Photo } from '../../types/photo.type';

@Component({
  selector: 'app-photos-overview',
  templateUrl: './photos-overview.component.html',
  styleUrls: ['./photos-overview.component.scss'],
})
export class PhotosOverviewComponent implements OnInit {
  title = 'Photos';
  numberOfColumnsToDisplay = 3;
  indexOfLastLoadedPhoto = 0;
  maxNumberOfDisplayedPhotosPerLoad = 30;
  searchOptions: SearchOptions = {} as SearchOptions;

  lastScrolledPosition = 0;
  displaySearchBarError: boolean = false;

  photos: Photo[] = [];
  displayedPhotos: Photo[][] = [];

  isLoading = false;

  constructor(
    private readonly photosService: PhotosService,
    private readonly router: Router,
    private loaderService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.photosService.resetPagination();
    this.initializeColumns();
    this.getPhotos();
  }

  onSearchTermChange(searchOptions: SearchOptions): void {
    this.searchOptions = searchOptions;

    this.displaySearchBarError =
      this.searchOptions.filterOptions.length === 0
        ? false
        : this.searchOptions.term.split(',').length !==
          this.searchOptions.filterOptions.length;

    if (this.displaySearchBarError) {
      return;
    }

    this.clearPhotos();
    this.getPhotos();
  }

  updateDisplayedPhotos(): void {
    this.getPhotosToDisplayForCurrentPage();
  }

  private clearPhotos(): void {
    this.photosService.resetPagination();
    this.displayedPhotos = [];
    this.photos = [];

    this.initializeColumns();
    this.indexOfLastLoadedPhoto = 0;
    this.maxNumberOfDisplayedPhotosPerLoad = 30;
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
    this.photosService
      .getPhotos(this.searchOptions)
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
