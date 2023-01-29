import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PhotoDetailsComponent } from './components/photos-overview/photo-details/photo-details.component';
import { PhotosOverviewComponent } from './components/photos-overview/photos-overview.component';

const routes: Routes = [
  { path: '', component: PhotosOverviewComponent },
  { path: ':id', component: PhotoDetailsComponent },
  { path: '**', component: PhotosOverviewComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PhotosRoutingModule {}
