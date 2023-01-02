import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhotosRoutingModule } from './photos-routing.module';
import { PhotosOverviewComponent } from './components/photos-overview/photos-overview.component';
import { PhotoDetailsComponent } from './components/photos-overview/photo-details/photo-details.component';
import { PhotoCardComponent } from './components/photos-overview/photo-card/photo-card.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    PhotosOverviewComponent,
    PhotoDetailsComponent,
    PhotoCardComponent
  ],
  imports: [
    CommonModule,
    PhotosRoutingModule,
    SharedModule
  ]
})
export class PhotosModule { }
