import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UpdateListingPage } from './update-listing.page';

describe('UpdateListingPage', () => {
  let component: UpdateListingPage;
  let fixture: ComponentFixture<UpdateListingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateListingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UpdateListingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
