import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhotosRoutingModule } from './photos-routing.module';
import { PhotosOverviewComponent } from './components/photos-overview/photos-overview.component';
import { PhotoDetailsComponent } from './components/photos-overview/photo-details/photo-details.component';
import { PhotoCardComponent } from './components/photos-overview/photo-card/photo-card.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { PhotoInfoComponent } from './components/photos-overview/photo-details/photo-info/photo-info.component';
import { PhotoAdditionalInfoComponent } from './components/photos-overview/photo-details/photo-additional-info/photo-additional-info.component';
import { ImageAdjustmentComponent } from './components/photos-overview/photo-details/image-adjustment/image-adjustment-component';
import { MatSliderModule } from '@angular/material/slider';
import { ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
  declarations: [
    PhotosOverviewComponent,
    PhotoDetailsComponent,
    PhotoCardComponent,
    PhotoInfoComponent,
    PhotoAdditionalInfoComponent,
    ImageAdjustmentComponent,
  ],
  imports: [
    CommonModule,
    PhotosRoutingModule,
    SharedModule,
    HttpClientModule,
    MatSliderModule,
    ReactiveFormsModule,
    MatToolbarModule,
  ],
  exports: [
    PhotoCardComponent
  ]
})
export class PhotosModule {}
