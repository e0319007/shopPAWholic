import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(private cartService: CartService,
              private utilityService: UtilityService) { }

  password : string;
  email: string;
  ngOnInit() {
    this.email = this.utilityService.getEmail();
    this.password = this.utilityService.getPassword();
  }

  initialiseCart() {
    this.cartService.initialiseCart();
  }

}
