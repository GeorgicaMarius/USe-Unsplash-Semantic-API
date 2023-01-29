import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Collection } from '../../../types/collection.type';

@Component({
  selector: 'app-collection-card',
  templateUrl: './collection-card.component.html',
  styleUrls: ['./collection-card.component.scss'],
})
export class CollectionCardComponent {
  @Input() collection!: Collection;

  @Output() openedDetails: EventEmitter<Collection> = new EventEmitter();

  onOpenCollectionDetails(): void {
    this.openedDetails.emit(this.collection);
  }
}
