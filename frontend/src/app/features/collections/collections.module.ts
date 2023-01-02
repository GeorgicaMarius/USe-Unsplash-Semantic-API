import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollectionsRoutingModule } from './collections-routing.module';
import { CollectionsOverviewComponent } from './components/collections-overview/collections-overview.component';


@NgModule({
  declarations: [
    CollectionsOverviewComponent
  ],
  imports: [
    CommonModule,
    CollectionsRoutingModule
  ]
})
export class CollectionsModule { }
