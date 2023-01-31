import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs';
import { PhotosService } from 'src/app/features/photos/services/photos.service';
import { Photo } from 'src/app/features/photos/types/photo.type';
import { CollectionsService } from '../../../services/collections.service';

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.scss'],
})
export class CollectionComponent {
  title: string = '';
  id: string = '';

  indexOfLastLoadedPhoto = 0;
  maxNumberOfDisplayedPhotosPerLoad = 30;
  numberOfColumnsToDisplay: number = 3;
  displayedPhotos: Photo[][] = [];
  photos: Photo[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly collectionsService: CollectionsService,
    private readonly photosService: PhotosService
  ) {
    this.getIdFromUrl();
    this.initializeColumns();
  }

  updateDisplayedPhotos(): void {
    this.updatePhotoColumns();

    const remaingPhotosNumberLessThanLoadSize =
      this.photos.length <
      this.indexOfLastLoadedPhoto + this.maxNumberOfDisplayedPhotosPerLoad;

    if (remaingPhotosNumberLessThanLoadSize) {
      this.indexOfLastLoadedPhoto = this.photos.length;
      return;
    }

    this.indexOfLastLoadedPhoto += this.maxNumberOfDisplayedPhotosPerLoad;
  }

  getIdFromUrl(): void {
    this.activatedRoute.paramMap.pipe(first()).subscribe((map) => {
      this.id = map.get('id') ?? '';
      this.getCollection();
    });
  }

  onOpenedDetails(photo: Photo): void {
    this.router.navigateByUrl(`${this.router.url}/${photo.photoId}`);
  }

  onClickedBackButton(): void {
    this.router.navigateByUrl(this.getCollectionsOverviewPath());
  }

  private getCollectionsOverviewPath(): string {
    let index = this.router.url.lastIndexOf('/');
    index = index == -1 ? this.router.url.length : index;
    return this.router.url.substring(0, index);
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

  private getCollection(): void {
    this.collectionsService
      .getCollection(this.id)
      .pipe(first())
      .subscribe((collection) => {
        this.title = collection.collectionTitle;
        this.getCollectionPhotos(collection.photoIds);
      });
  }

  private getCollectionPhotos(photoIds: string[]): void {
    this.photosService
      .getPhotosById(photoIds)
      .pipe(first())
      .subscribe((photos) => {
        this.photos = photos;
        this.updateDisplayedPhotos();
      });
  }
}
