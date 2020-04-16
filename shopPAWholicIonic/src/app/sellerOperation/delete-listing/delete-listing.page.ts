import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { Listing } from 'src/app/listing';
import { ListingService } from 'src/app/listing.service';

@Component({
  selector: 'app-delete-listing',
  templateUrl: './delete-listing.page.html',
  styleUrls: ['./delete-listing.page.scss'],
})
export class DeleteListingPage implements OnInit {

  constructor(private listingService: ListingService,
              private activatedRoute: ActivatedRoute,
              private router: Router) { }

  listingId: number;
  listingToDelete: Listing;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  ngOnInit() {
    this.listingId = parseInt(this.activatedRoute.snapshot.paramMap.get('listingId'));
    this.listingService.retrieveListingById(this.listingId).subscribe(
      response => {
        this.listingToDelete = response.listing;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        console.log("*****DeleteListingComponent.ts" + error);
      }
    )
  }

  deleteProduct() {
    this.listingService.deleteListing(this.listingId).subscribe(
      response => {
        this.resultSuccess = true;
      },
      error => {
        this.error = true;
        this.errorMessage = error
      }
    )
  }
}


