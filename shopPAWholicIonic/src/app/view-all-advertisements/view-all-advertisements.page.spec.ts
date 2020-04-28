import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewAllAdvertisementsPage } from './view-all-advertisements.page';

describe('ViewAllAdvertisementsPage', () => {
  let component: ViewAllAdvertisementsPage;
  let fixture: ComponentFixture<ViewAllAdvertisementsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllAdvertisementsPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewAllAdvertisementsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
