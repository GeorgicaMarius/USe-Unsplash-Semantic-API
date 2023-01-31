import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-panel-header',
  templateUrl: './panel-header.component.html',
  styleUrls: ['./panel-header.component.scss'],
})
export class PanelHeaderComponent {
  @Input() title: string = '';
  @Input() displayBackButton: boolean = false;
  @Input() isCollectionMenu: boolean = false;
  @Input() goBackText: string = 'Overview';

  @Output() clickedBackButton: EventEmitter<void> = new EventEmitter();
  @Output() toggled: EventEmitter<boolean> = new EventEmitter();

  onClickedBackButton(): void {
    this.clickedBackButton.emit();
  }

  onToggleChanged(toggle: any): void {
    this.toggled.emit(toggle.checked);
  }
}
