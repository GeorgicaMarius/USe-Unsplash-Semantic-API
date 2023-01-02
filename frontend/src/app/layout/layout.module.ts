import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout.component';
import { HeaderComponent } from './components/header/header.component';
import { SharedModule } from '../shared/shared.module';
import { FilterMenuComponent } from './components/filter-menu/filter-menu.component';
import { RoutingMenuComponent } from './components/routing-menu/routing-menu.component';



@NgModule({
  declarations: [
    LayoutComponent,
    HeaderComponent,
    FilterMenuComponent,
    RoutingMenuComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    LayoutComponent
  ]
})
export class LayoutModule { }
