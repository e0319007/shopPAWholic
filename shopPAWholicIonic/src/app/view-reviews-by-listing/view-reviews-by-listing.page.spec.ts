import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewReviewsByListingPage } from './view-reviews-by-listing.page';

describe('ViewReviewsByListingPage', () => {
  let component: ViewReviewsByListingPage;
  let fixture: ComponentFixture<ViewReviewsByListingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewReviewsByListingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewReviewsByListingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
