import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CreateOrderSuccessPagePage } from './create-order-success-page.page';

describe('CreateOrderSuccessPagePage', () => {
  let component: CreateOrderSuccessPagePage;
  let fixture: ComponentFixture<CreateOrderSuccessPagePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateOrderSuccessPagePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateOrderSuccessPagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
