import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { DeleteListingPage } from './delete-listing.page';

describe('DeleteListingPage', () => {
  let component: DeleteListingPage;
  let fixture: ComponentFixture<DeleteListingPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeleteListingPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteListingPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
