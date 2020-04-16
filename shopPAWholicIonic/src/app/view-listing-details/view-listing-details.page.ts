import { Component, OnInit } from '@angular/core';
import { Listing } from '../listing';
import { Router, ActivatedRoute } from '@angular/router';
import { ListingService } from '../listing.service';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-view-listing-details',
  templateUrl: './view-listing-details.page.html',
  styleUrls: ['./view-listing-details.page.scss'],
})
export class ViewListingDetailsPage implements OnInit {

  listingId: number;
  listingToView: Listing;
  retrieveListingError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  constructor(private router: Router, private activeRoute: ActivatedRoute, private listingService: ListingService, private alertController: AlertController) {
    this.retrieveListingError = false;
    this.error = false;
    this.resultSuccess = false;
  }

  ngOnInit() {
    this.listingId = parseInt(this.activeRoute.snapshot.paramMap.get('listingId'));
    console.log("called view listing method with listing id: " + this.listingId);
    this.refreshListing();

  }

  refreshListing() {
    console.log("****IN REFRESH")
    this.listingService.retrieveListingById(this.listingId).subscribe(
      response => {
        console.log("******** refreshed listing and got back response with listing name: " );
        this.listingToView = response.listing;
        console.log(this.listingToView == null);
      },
      error => {
        this.retrieveListingError = true;
        console.log('********* ViewListingDetailsPage.ts: ' + error);
      }
    );
  }

  ionViewWillEnter() 
	{
		this.refreshListing();
	}
	
  updateListing() {
    this.router.navigate(["/updateListing" + this.listingId]);
  }

  async deleteListing() {

  }
}
