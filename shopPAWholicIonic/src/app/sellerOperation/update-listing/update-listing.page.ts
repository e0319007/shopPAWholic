import { Component, OnInit } from '@angular/core';
import { Listing } from 'src/app/listing';
import { Category } from 'src/app/category';
import { Tag } from 'src/app/tag';
import { Router, ActivatedRoute } from '@angular/router';
import { ListingService } from 'src/app/listing.service';
import { CategoryService } from 'src/app/category.service';
import { TagService } from 'src/app/tag.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-update-listing',
  templateUrl: './update-listing.page.html',
  styleUrls: ['./update-listing.page.scss'],
})
export class UpdateListingPage implements OnInit {

  submitted: boolean;
	listingId: number;	
	listingToUpdate: Listing;
	retrieveListingError: boolean;
	
	categoryId: string;
	tagIds: string[];
	
	categories: Category[];
	tags: Tag[];
	
	resultSuccess: boolean;
	resultError: boolean;
	message: string;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private listingService: ListingService,
              private categoryService: CategoryService,
              private tagService: TagService) 
  {
    this.submitted = false;
    this.retrieveListingError = false;
    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit() {
    this.listingId = parseInt(this.activatedRoute.snapshot.paramMap.get('listingId'));
    this.listingService.retrieveListingById(this.listingId).subscribe(
      response => {
        this.listingToUpdate = response.listing;
        this.categoryId = this.listingToUpdate.category.categoryId.toString();

        this.tagIds = new Array();
        for (let tag of this.listingToUpdate.tags) {
          this.tagIds.push(tag.tagId.toString());
        }
      },
      error => {
        this.retrieveListingError = true;
        console.log("**********UpdateListingpage.ts" + error);
      }
    );
    this.categoryService.retrieveAllLeafCategories().subscribe(
      response => {
        this.categories = response.categoryEntities;
      },
      error => {
        console.log('********** UpdateListingPage.ts: ' + error);
      }
    );

    this.tagService.retrieveAllTags().subscribe(
			response => {
				this.tags = response.tags;
			},
			error => {
				console.log('********** UpdateListingPage.ts: ' + error);
			}
		);
  }

  update(updateListingForm: NgForm) {
    console.log("Updating called*************")
    let longTagIds: number[] = new Array();
    for (let tagId of this.tagIds) {
      longTagIds.push(parseInt(tagId));
    }
    this.submitted = true;
    if(updateListingForm.valid) {
      this.listingService.updateListing(this.listingToUpdate, parseInt(this.categoryId), longTagIds).subscribe(
        response => {					
					this.resultSuccess = true;
					this.resultError = false;
					this.message = "Listing updated successfully";
				},
				error => {
					this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while updating the listing: " + error;
					
					console.log('********** UpdateListingComponent.ts: ' + error);
				}
      );
    }
  } 

  addTag() {
    this.router.navigate(["/sellerOperation/createNewTag"]);
  }


  back()
	{
		this.router.navigate(["viewListingDetails/" + this.listingToUpdate.listingId]);
	}

}
