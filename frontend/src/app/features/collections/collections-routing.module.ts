import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PhotoDetailsComponent } from '../photos/components/photos-overview/photo-details/photo-details.component';
import { CollectionComponent } from './components/collections-overview/collection/collection.component';
import { CollectionsOverviewComponent } from './components/collections-overview/collections-overview.component';

const routes: Routes = [
  { path: '', component: CollectionsOverviewComponent },
  { path: ':id', component: CollectionComponent },
  { path: ':collectionId/:photoId', component: PhotoDetailsComponent },
  { path: '**', component: CollectionsOverviewComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CollectionsRoutingModule {}
