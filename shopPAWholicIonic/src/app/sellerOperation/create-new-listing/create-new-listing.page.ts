import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Listing } from 'src/app/listing';
import { Category } from 'src/app/category';
import { Tag } from 'src/app/tag';
import { ListingService } from 'src/app/listing.service';
import { CategoryService } from 'src/app/category.service';
import { TagService } from 'src/app/tag.service';

@Component({
  selector: 'app-create-new-listing',
  templateUrl: './create-new-listing.page.html',
  styleUrls: ['./create-new-listing.page.scss'],
})
export class CreateNewListingPage implements OnInit {

  submitted: boolean;
  newListing: Listing;
  categoryId: string;
  tagIds: string[];
  picture: string;

  categories: Category[];
  tags: Tag[];

  resultSuccess: boolean;
  resultError: boolean;
  message: string;
  
  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private listingService: ListingService, private categoryService: CategoryService,
              private tagService: TagService) 
  {
    console.log("Calling create new listing constructor**********")
    this.submitted = false;
    this.newListing = new Listing();
    this.resultError = false;
    this.resultSuccess = false;
  }

  ionViewWillEnter(){
    this.tagService.retrieveAllTags().subscribe(
      response => {
        this.tags = response.tags;
      },
      error => {
        console.log("Retrieve Tags in CreateNewListingPage: " + error);
      }
    )
  }

  ngOnInit() {
    this.categoryService.retrieveAllLeafCategories().subscribe(
      response => {
        this.categories = response.categoryEntities;
        console.log("Categories length: " + this.categories)
      },
      error => {
        console.log("Retrieve Categories in CreateNewListingPage: " + error);
      }
    );
    this.tagService.retrieveAllTags().subscribe(
      response => {
        this.tags = response.tags;
      },
      error => {
        console.log("Retrieve Tags in CreateNewListingPage: " + error);
      }
    )
  }

  create(createListingForm: NgForm) {
    let longTagIds: number[] = new Array();
    this.newListing.picture = this.picture;

    if(this.tagIds != null) {
      for (var i = 0; i < this.tagIds.length; i++) {
        longTagIds.push(parseInt(this.tagIds[i]));
      }
    }

    this.submitted = true;

    if(createListingForm.valid) {
      this.listingService.createListing(this.newListing, parseInt(this.categoryId), longTagIds).subscribe(
        response => {
          let newListingId: number = response.listingId;
          this.resultError = false;
          this.resultSuccess = true;
          this.message = "New listing " + newListingId + " created successfully!"

          createListingForm.reset();
          this.reset;
        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occured while creating the new product: " + error;
          console.log('********** CreateNewListingPage.ts: ' + error);
        }
      )
    }
  }

  clear() {
    this.submitted = false;
		this.newListing = new Listing();
  }

  private reset() {
    this.newListing  = new Listing();
    this.categoryId = "";
    this.tagIds = new Array();
    this.submitted = false;
  }

  addTag() {
    this.router.navigate(["/sellerOperation/createNewTag"]);
  }

}

