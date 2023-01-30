import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { MaterialDesignModule } from './material-design.module';
import { PanelHeaderComponent } from './components/panel-header/panel-header.component';
import { PanelFooterComponent } from './components/panel-footer/panel-footer.component';
import { ScrollWatchDirective } from './directives/scroll-watch.directive';
import { EmptyStringPipe } from './pipes/empty-string.pipe';
import { CustomCityCountryPipe } from './pipes/CustomCityCountryPipe';
import { LinkifyPipe } from './pipes/LinkifyPipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    SearchBarComponent,
    PanelHeaderComponent,
    PanelFooterComponent,
    ScrollWatchDirective,
    EmptyStringPipe,
    CustomCityCountryPipe,
    LinkifyPipe,
  ],
  imports: [
    CommonModule,
    MaterialDesignModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    MaterialDesignModule,
    SearchBarComponent,
    PanelHeaderComponent,
    PanelFooterComponent,
    ScrollWatchDirective,
    EmptyStringPipe,
    CustomCityCountryPipe,
    LinkifyPipe,
  ],
})
export class SharedModule {}
