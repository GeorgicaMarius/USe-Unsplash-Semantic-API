import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollectionsRoutingModule } from './collections-routing.module';
import { CollectionsOverviewComponent } from './components/collections-overview/collections-overview.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { LayoutModule } from 'src/app/layout/layout.module';
import { CoreModule } from 'src/app/core/core.module';
import { CollectionCardComponent } from './components/collections-overview/collection-card/collection-card.component';
import { PhotoStackComponent } from './components/collections-overview/photo-stack-component/photo-stack.component';
import { CollectionComponent } from './components/collections-overview/collection/collection.component';
import { PhotosModule } from '../photos/photos.module';

@NgModule({
  declarations: [
    CollectionsOverviewComponent,
    CollectionCardComponent,
    PhotoStackComponent,
    CollectionComponent,
  ],
  imports: [
    CommonModule,
    CollectionsRoutingModule,
    SharedModule,
    LayoutModule,
    CoreModule,
    PhotosModule,
  ],
})
export class CollectionsModule {}
