import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CollectionsOverviewComponent } from './components/collections-overview/collections-overview.component';

const routes: Routes = [{ path: '', component: CollectionsOverviewComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollectionsRoutingModule { }
