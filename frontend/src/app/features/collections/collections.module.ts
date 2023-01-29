import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollectionsRoutingModule } from './collections-routing.module';
import { CollectionsOverviewComponent } from './components/collections-overview/collections-overview.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { LayoutModule } from 'src/app/layout/layout.module';
import { CoreModule } from 'src/app/core/core.module';
import { CollectionCardComponent } from './components/collections-overview/collection-card/collection-card.component';


@NgModule({
  declarations: [
    CollectionsOverviewComponent,
    CollectionCardComponent
  ],
  imports: [
    CommonModule,
    CollectionsRoutingModule,
    SharedModule,
    LayoutModule,
    CoreModule
  ]
})
export class CollectionsModule { }
