import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-panel-header',
  templateUrl: './panel-header.component.html',
  styleUrls: ['./panel-header.component.scss'],
})
export class PanelHeaderComponent {
  @Input() title: string = 'Generic Title';
  @Input() displayBackButton: boolean = false;
  @Output() clickedBackButton: EventEmitter<void> = new EventEmitter();

  goBackText: string = 'Overview';

  onClickedBackButton(): void {
    this.clickedBackButton.emit();
  }
}
