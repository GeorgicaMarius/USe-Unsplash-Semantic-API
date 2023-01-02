import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  isSideMenuOpen = false;

  onClickMenuIcon(): void{
    this.isSideMenuOpen = !this.isSideMenuOpen;
    console.log(this.isSideMenuOpen);
  }

}
