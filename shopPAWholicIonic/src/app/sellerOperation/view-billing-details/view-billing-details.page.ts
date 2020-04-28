import { Component, OnInit } from '@angular/core';
import { BillingDetailService } from 'src/app/billing-detail.service';
import { BillingDetail } from 'src/app/billing-detail';

@Component({
  selector: 'app-view-billing-details',
  templateUrl: './view-billing-details.page.html',
  styleUrls: ['./view-billing-details.page.scss'],
})
export class ViewBillingDetailsPage implements OnInit {

  billingDetails: BillingDetail[];
  noBilling: boolean;
  
  constructor(private billingService: BillingDetailService,) 
  {
    this.noBilling = false;
  }

  ngOnInit() {
    this.refresh();
    
  }

  refresh(){
    this.billingService.retrieveAllSellerBillingDetails().subscribe(
      response => {
        this.billingDetails = response.billingDetails;
        if (this.billingDetails == null || this.billingDetails.length == 0) this.noBilling = true;
        else this.noBilling = false;
        for (let billingDetail of this.billingDetails) {
          billingDetail.creditCardDetail = "**** **** **** " + billingDetail.creditCardDetail.substring(14);
        }
      },
      error => {
        console.log("error while retrieveing billing details: " + error);
      }
    )
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]','');
  }



}
