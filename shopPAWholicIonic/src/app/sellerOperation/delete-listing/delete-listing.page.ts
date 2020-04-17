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
              private router: Router)
  {
    this.error = false;
		this.resultSuccess = false;
  }

  listingId: number;
  listingToDelete: Listing;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  ngOnInit() {
    this.listingId = parseInt(this.activatedRoute.snapshot.paramMap.get('listingId'));
    console.log("Listing to delete with id: " + this.listingId);
    this.listingService.retrieveListingById(this.listingId).subscribe(
      response => {
        this.listingToDelete = response.listing;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        console.log("*****DeleteListingComponent.ts" + error);
      }
    );
    console.log("error: " + this.error );
    console.log("listing==null?" + this.listingToDelete == null );
    console.log("resultSuccess?" + this.resultSuccess)
  }

  deleteListing() {
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

  backViewAll() {
    this.router.navigate(["/viewAllListings"]);
  }

  back()
	{
		if(this.resultSuccess)
		{
			this.router.navigate(["/viewAllListings"]);
		}
		else
		{
			this.router.navigate(["/viewListingDetails/" + this.listingToDelete.listingId]);
		}
	}
}


