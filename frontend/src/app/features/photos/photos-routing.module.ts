import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PhotosOverviewComponent } from './components/photos-overview/photos-overview.component';

const routes: Routes = [{ path: '', component: PhotosOverviewComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PhotosRoutingModule { }
