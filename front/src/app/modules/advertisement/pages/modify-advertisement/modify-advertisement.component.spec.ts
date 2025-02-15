import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyAdvertisementComponent } from './modify-advertisement.component';

describe('UpdateAdvertisementComponent', () => {
  let component: ModifyAdvertisementComponent;
  let fixture: ComponentFixture<ModifyAdvertisementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifyAdvertisementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyAdvertisementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
