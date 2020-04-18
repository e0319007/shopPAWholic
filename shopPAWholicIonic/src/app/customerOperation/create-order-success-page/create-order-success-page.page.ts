import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-order-success-page',
  templateUrl: './create-order-success-page.page.html',
  styleUrls: ['./create-order-success-page.page.scss'],
})
export class CreateOrderSuccessPagePage implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  viewOrders() {
    this.router.navigate(["/viewAllOrders"])
  }

  shopMore() {
    this.router.navigate(["/viewAllListings"])
  }


}
