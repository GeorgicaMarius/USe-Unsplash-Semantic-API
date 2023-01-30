import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { first } from 'rxjs';
import { PhotosService } from 'src/app/features/photos/services/photos.service';
import { SearchOptions } from 'src/app/shared/types/search-options.type';
import { CollectionsService } from '../../services/collections.service';
import { Collection } from '../../types/collection.type';

@Component({
  selector: 'app-collections-overview',
  templateUrl: './collections-overview.component.html',
  styleUrls: ['./collections-overview.component.scss'],
})
export class CollectionsOverviewComponent {
  title = 'Collections';
  numberOfColumnsToDisplay = 3;
  indexOfLastLoadedCollection = 0;
  maxNumberOfDisplayedCollectionsPerLoad = 30;
  searchOptions: SearchOptions = {} as SearchOptions;

  lastScrolledPosition = 0;
  shuffleOnClick = true;

  collections: Collection[] = [];
  displayedCollections: Collection[][] = [];

  isLoading = false;

  constructor(
    private readonly collectionsService: CollectionsService,
    private readonly photosService: PhotosService,
    private readonly router: Router,
    private loaderService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.collectionsService.resetPagination();
    this.initializeColumns();
    this.getCollections();
  }

  onToggleChange(value: any): void {
    this.shuffleOnClick = value;
  }

  onSearchTermChange(searchOptions: SearchOptions) {
    this.searchOptions = searchOptions;
    this.clearCollections();
    this.getCollections();
  }

  onOpenedDetails(collection: Collection): void {
    this.router.navigateByUrl(`${this.router.url}/${collection.collectionId}`);
  }

  updateDisplayedCollections(): void {
    this.getCollectionsToDisplayForCurrentPage();
  }

  private clearCollections(): void {
    this.collectionsService.resetPagination();
    this.displayedCollections = [];
    this.collections = [];

    this.initializeColumns();
    this.indexOfLastLoadedCollection = 0;
    this.maxNumberOfDisplayedCollectionsPerLoad = 30;
  }

  private initializeColumns() {
    for (
      let columnIndex = 0;
      columnIndex < this.numberOfColumnsToDisplay;
      columnIndex++
    ) {
      this.displayedCollections.push([]);
    }
  }

  private getCollections(): void {
    this.collectionsService
      .getCollections(this.searchOptions.term)
      .pipe(first())
      .subscribe((collections) => {
        this.collections.push(...collections);
        this.updateCollectionsThumbnail();
      });
  }

  private updateCollectionsThumbnail(): void {
    const thumbnailsIds = this.collections.map(
      (collection) => collection.photoIds[0]
    );

    this.photosService
      .getPhotosById(thumbnailsIds)
      .pipe(first())
      .subscribe((photos) => {
        photos.forEach((photo) => {
          const index = this.collections.findIndex(
            (collection) => photo.photoId === collection.photoIds[0]
          );
          this.collections[index].photoThumbnailUrl = photo.photoImageUrl;
        });

        this.updateDisplayedCollections();
      });
  }

  private getCollectionsToDisplayForCurrentPage(): void {
    this.updateCollectionsColumns();

    const remaingCollectionsNumberLessThanLoadSize =
      this.collections.length <
      this.indexOfLastLoadedCollection +
        this.maxNumberOfDisplayedCollectionsPerLoad;

    if (remaingCollectionsNumberLessThanLoadSize) {
      this.indexOfLastLoadedCollection = this.collections.length;
      return;
    }

    this.indexOfLastLoadedCollection +=
      this.maxNumberOfDisplayedCollectionsPerLoad;

    if (this.shouldRetrieveMoreCollections()) {
      this.getCollections();
    }
  }

  private updateCollectionsColumns(): void {
    for (
      let columnIndex = 0;
      columnIndex < this.numberOfColumnsToDisplay;
      columnIndex++
    ) {
      this.updateColumn(columnIndex);
    }
  }

  private updateColumn(columnIndex: number) {
    let columnCollections: Collection[] = [];
    const startIndex = this.indexOfLastLoadedCollection + columnIndex;
    const endIndex =
      this.indexOfLastLoadedCollection +
      this.maxNumberOfDisplayedCollectionsPerLoad;

    for (
      let index = startIndex;
      index < endIndex;
      index += this.numberOfColumnsToDisplay
    ) {
      columnCollections.push(this.collections[index]);
    }

    this.displayedCollections[columnIndex].push(...columnCollections);
  }

  private shouldRetrieveMoreCollections(): boolean {
    return (
      this.collections.length - this.indexOfLastLoadedCollection <
      this.maxNumberOfDisplayedCollectionsPerLoad
    );
  }
}
