import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { MaterialDesignModule } from './material-design.module';
import { PanelHeaderComponent } from './components/panel-header/panel-header.component';
import { PanelFooterComponent } from './components/panel-footer/panel-footer.component';
import { ScrollWatchDirective } from './directives/scroll-watch.directive';



@NgModule({
  declarations: [
    SearchBarComponent,
    PanelHeaderComponent,
    PanelFooterComponent,
    ScrollWatchDirective,
  ],
  imports: [CommonModule, MaterialDesignModule],
  exports: [
    MaterialDesignModule,
    SearchBarComponent,
    PanelHeaderComponent,
    PanelFooterComponent,
    ScrollWatchDirective,
  ],
})
export class SharedModule {}
