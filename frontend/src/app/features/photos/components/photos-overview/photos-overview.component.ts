import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { first } from 'rxjs';
import { PhotosOverviewService } from '../../services/photos-overview.service';
import { Photo } from '../../types/photo-response.type';

@Component({
  selector: 'app-photos-overview',
  templateUrl: './photos-overview.component.html',
  styleUrls: ['./photos-overview.component.scss'],
})
export class PhotosOverviewComponent implements OnInit {
  title = 'Photos';

  photos: Photo[] = [];
  displayedPhotos: Photo[] = [];

  currentPage = 0;
  pageSize = 10;

  constructor(
    private readonly photosOverviewService: PhotosOverviewService,
    private ngxService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.getPhotos();
  }

  onPageChanged(pageEvent: PageEvent): void {
    this.currentPage = pageEvent.pageIndex;
    this.pageSize = pageEvent.pageSize;

    this.updateDisplayedPhotos();
  }

  private updateDisplayedPhotos(): void {
    this.ngxService.start();
    this.displayedPhotos = this.getPhotosToDisplayForCurrentPage();
    this.ngxService.stop();
  }

  private getPhotos(): void {
    this.photosOverviewService
      .getPhotos()
      .pipe(first())
      .subscribe((photos) => {
        this.photos = photos;
        this.updateDisplayedPhotos();
      });
  }

  private getPhotosToDisplayForCurrentPage(): Photo[] {
    const currentPhotosIndex = this.currentPage * this.pageSize;

    const remaingPhotosNumberLessThanPageSize =
      this.photos.length < currentPhotosIndex + this.pageSize;

    if (remaingPhotosNumberLessThanPageSize) {
      return this.photos.slice(this.currentPage * this.pageSize);
    }

    return this.photos.slice(
      this.currentPage * this.pageSize,
      currentPhotosIndex + this.pageSize
    );
  }
}
