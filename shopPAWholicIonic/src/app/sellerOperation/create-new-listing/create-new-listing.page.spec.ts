import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CreateNewListingPage } from './create-new-listing.page';

describe('CreateNewListingPage', () => {
  let component: CreateNewListingPage;
  let fixture: ComponentFixture<CreateNewListingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewListingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateNewListingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
