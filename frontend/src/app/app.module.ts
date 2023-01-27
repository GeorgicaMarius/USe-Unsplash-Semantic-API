import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {
  NgxUiLoaderHttpModule,
  NgxUiLoaderModule,
  NgxUiLoaderRouterModule,
} from 'ngx-ui-loader';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './layout/layout.module';
import { SharedModule } from './shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    LayoutModule,
    SharedModule,
    NgxUiLoaderModule.forRoot({
      hasProgressBar: false,
    }),
    NgxUiLoaderRouterModule.forRoot({ showForeground: true }),
    NgxUiLoaderHttpModule.forRoot({ showForeground: true }),
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
