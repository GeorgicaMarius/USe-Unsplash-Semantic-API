import { Directive, ElementRef, EventEmitter, HostListener, Output } from "@angular/core";

@Directive({
  selector: '[scrollWatch]',
})
export class ScrollWatchDirective {
  @Output() scrollReachedLowerBound: EventEmitter<void> = new EventEmitter();

  constructor(private el: ElementRef) {}

  @HostListener('scroll', ['$event'])
  scrollIt(scroll: any) {
    if(scroll.target.scrollTop + 600 >= scroll.target.scrollTopMax){
      this.scrollReachedLowerBound.emit();
    }
  }
}
