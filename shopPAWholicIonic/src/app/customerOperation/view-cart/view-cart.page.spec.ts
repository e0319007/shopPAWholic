import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewCartPage } from './view-cart.page';

describe('ViewCartPage', () => {
  let component: ViewCartPage;
  let fixture: ComponentFixture<ViewCartPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCartPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewCartPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
