import { Component, OnInit } from '@angular/core';
import { BillingDetail } from '../billing-detail';
import { BillingDetailService } from '../billing-detail.service';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-view-billing-detail',
  templateUrl: './view-billing-detail.page.html',
  styleUrls: ['./view-billing-detail.page.scss'],
})
export class ViewBillingDetailPage implements OnInit {

  billingDetails: BillingDetail[];
  errorMessage: string;

  constructor(private utilityService: UtilityService,private billingDetailService: BillingDetailService) { }

  ngOnInit() {
    if (this.utilityService.isCustomer) {
      this.billingDetailService.retrieveAllCustomerBillingDetails().subscribe(
        response => {
          this.billingDetails = response.billingDetails;
        },
        error => {
          this.errorMessage = error;
        }
      );
    } else {
      this.billingDetailService.retrieveAllSellerBillingDetails().subscribe(
        response => {
          this.billingDetails = response.billingDetails;
        },
        error => {
          this.errorMessage = error;
        }
      );
    }
  }

}
