import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'photos', loadChildren: () => import('./features/photos/photos.module').then(m => m.PhotosModule) },
  { path: 'collections', loadChildren: () => import('./features/collections/collections.module').then(m => m.CollectionsModule) },
  { path: '**', redirectTo: 'photos' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
