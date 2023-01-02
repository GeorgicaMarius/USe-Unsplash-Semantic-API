import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutingMenuComponent } from './routing-menu.component';

describe('RoutingMenuComponent', () => {
  let component: RoutingMenuComponent;
  let fixture: ComponentFixture<RoutingMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoutingMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoutingMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
