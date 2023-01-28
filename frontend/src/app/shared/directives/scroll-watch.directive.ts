import {Directive, ElementRef, EventEmitter, HostListener, Output} from "@angular/core";

@Directive({
  selector: '[scrollWatch]',
})
export class ScrollWatchDirective  {
  @Output() scrollReachedLowerBound: EventEmitter<void> = new EventEmitter();

  constructor(private el: ElementRef) {
  }

  @HostListener('scroll', ['$event'])
  scrollIt(scroll: any) {
    if (scroll.target.scrollTop + scroll.target.clientHeight  + 600 >= scroll.target.scrollHeight) {
      this.scrollReachedLowerBound.emit();
    }
  }
}
