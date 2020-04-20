import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewMyListingsPage } from './view-my-listings.page';

describe('ViewMyListingsPage', () => {
  let component: ViewMyListingsPage;
  let fixture: ComponentFixture<ViewMyListingsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewMyListingsPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewMyListingsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
