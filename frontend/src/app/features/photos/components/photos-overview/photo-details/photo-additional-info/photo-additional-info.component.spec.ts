import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoAdditionalInfoComponent } from './photo-additional-info.component';

describe('PhotoAdditionalInfoComponent', () => {
  let component: PhotoAdditionalInfoComponent;
  let fixture: ComponentFixture<PhotoAdditionalInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PhotoAdditionalInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PhotoAdditionalInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
