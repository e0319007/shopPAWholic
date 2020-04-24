import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CreateAdvertisementPage } from './create-advertisement.page';

describe('CreateAdvertisementPage', () => {
  let component: CreateAdvertisementPage;
  let fixture: ComponentFixture<CreateAdvertisementPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAdvertisementPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateAdvertisementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
