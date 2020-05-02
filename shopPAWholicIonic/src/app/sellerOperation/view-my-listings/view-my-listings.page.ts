<<<<<<< HEAD
import { Component, OnInit } from '@angular/core';
import { Listing } from 'src/app/listing';
import { Router } from '@angular/router';
import { ListingService } from 'src/app/listing.service';
import { UtilityService } from 'src/app/utility.service';

@Component({
  selector: 'app-view-my-listings',
  templateUrl: './view-my-listings.page.html',
  styleUrls: ['./view-my-listings.page.scss'],
})
export class ViewMyListingsPage implements OnInit {

  listings: Listing[];
  allListings:Listing[];
  filteredListings: Listing[];
  noListing: boolean;


  constructor(private router: Router, 
              private listingService: ListingService,
              private utilityService: UtilityService) 
  {
    this.noListing = false;
  }

  ngOnInit() {
    this.refreshListings();
    console.log("ngOnInit ")
  }

  ionViewWillEnter() {
    this.refreshListings();
    console.log("ion will enter ")
  }

  filter(event) {
    console.log("filtering********");
    let searchTerm: string = event.srcElement.value;
    if(!searchTerm) {
      return;
    }
    this.filteredListings = new Array();
    this.filteredListings = this.listings.filter(listing => {
      if (listing.name && searchTerm) {
        if (listing.name.toLowerCase().indexOf(searchTerm.toLowerCase()) > -1) {
          return true;
        }
        return false;
      }
    });
  }

  refreshListings() {
    console.log("refreshing")
    
    this.listingService.retrieveAllListings().subscribe(
      response => {
        this.allListings = new Array();
        this.listings = new Array();
        this.filteredListings = new Array();
        this.allListings = response.listings;
        for (let listing of this.allListings) {
          if (listing.seller.email == this.utilityService.getEmail()) {
            this.listings.push(listing);
          }
        }
        if (this.listings.length == 0) {
          this.noListing = true;
        } else this.noListing = false;
        this.filteredListings = this.listings;
        console.log("I have listings length: " + this.filteredListings.length);
        console.log("I have no listings: " + this.noListing);
      },
      error => {
        console.log('********* ViewAllListingsPage.ts: ' + error);
      }
    )
  }

  viewListingDetails(event, listing) {
    this.router.navigate(["/viewListingDetails/" + listing.listingId]);
  }

  createListing() {
    this.router.navigate(["/sellerOperation/createNewListing"]);
  }
}
=======
import { Component, OnInit } from '@angular/core';
import { Listing } from 'src/app/listing';
import { Router } from '@angular/router';
import { ListingService } from 'src/app/listing.service';
import { UtilityService } from 'src/app/utility.service';

@Component({
  selector: 'app-view-my-listings',
  templateUrl: './view-my-listings.page.html',
  styleUrls: ['./view-my-listings.page.scss'],
})
export class ViewMyListingsPage implements OnInit {

  listings: Listing[];
  allListings:Listing[];
  filteredListings: Listing[];
  noListing: boolean;


  constructor(private router: Router, 
              private listingService: ListingService,
              private utilityService: UtilityService) 
  {
    this.noListing = false;
  }

  ngOnInit() {
    this.refreshListings();
  }

  ionViewWillEnter() {
    this.refreshListings();
  }

  filter(event) {
    console.log("filtering********");
    let searchTerm: string = event.srcElement.value;
    if(!searchTerm) {
      return;
    }
    this.filteredListings = this.listings.filter(listing => {
      if (listing.name && searchTerm) {
        if (listing.name.toLowerCase().indexOf(searchTerm.toLowerCase()) > -1) {
          return true;
        }
        return false;
      }
    });
  }

  refreshListings() {
    this.listingService.retrieveAllListings().subscribe(
      response => {
        this.allListings = new Array();
        this.listings = new Array();
        this.filteredListings = new Array();
        this.allListings = response.listings;
        for (let listing of this.allListings) {
          if (listing.seller.email == this.utilityService.getEmail()) {
            this.listings.push(listing);
          }
        }
        if (this.listings.length == 0) {
          this.noListing = true;
        } else this.noListing = false;
        this.filteredListings = this.listings;
        console.log("I have listings length: " + this.filteredListings.length);
        console.log("I have no listings: " + this.noListing);
      },
      error => {
        console.log('********* ViewAllListingsPage.ts: ' + error);
      }
    )
  }

  viewListingDetails(event, listing) {
    this.router.navigate(["/viewListingDetails/" + listing.listingId]);
  }

  createListing() {
    this.router.navigate(["/sellerOperation/createNewListing"]);
  }
}
>>>>>>> master
