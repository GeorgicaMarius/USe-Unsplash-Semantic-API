import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';

const materialDesignImport = [
  MatCardModule,
  MatInputModule,
  MatSidenavModule,
  MatIconModule,
  MatPaginatorModule,
  MatButtonModule
];

@NgModule({
  declarations: [],
  imports: [
    ...materialDesignImport,
    CommonModule
  ],
  exports: [
    ...materialDesignImport
  ]
})
export class MaterialDesignModule { }
