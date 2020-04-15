import { Component, OnInit } from '@angular/core';
import { Listing } from '../listing';
import { Router } from '@angular/router';
import { ListingService } from '../listing.service';

@Component({
  selector: 'app-view-all-listings',
  templateUrl: './view-all-listings.page.html',
  styleUrls: ['./view-all-listings.page.scss'],
})
export class ViewAllListingsPage implements OnInit {

  listings: Listing[];

  constructor(private router: Router, private listingService: ListingService) { }

  ngOnInit() {
    this.refreshListings();
  }

  ionViewWillEnter() {
    this.refreshListings();
  }

  refreshListings() {
    this.listingService.retrieveAllListings().subscribe(
      response => {
        this.listings = response.listings;
      },
      error => {
        console.log('********* ViewAllListingsPage.ts: ' + error);
      }
    )
  }

  viewListingDetails(event, listing) {
    this.router.navigate(["/viewListingDetails/" + listing.listingId]);
  }

}
