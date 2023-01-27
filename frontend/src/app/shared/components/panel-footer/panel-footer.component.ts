import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-panel-footer',
  templateUrl: './panel-footer.component.html',
  styleUrls: ['./panel-footer.component.scss'],
})
export class PanelFooterComponent {
  @Input() numberOfItems: number = 0;
  @Input() pageSize: number = 0;

  @Output() pageChanged: EventEmitter<PageEvent> = new EventEmitter();

  onPageChanged(pageEvent: PageEvent): void {
    this.pageChanged.emit(pageEvent);
  }
}
