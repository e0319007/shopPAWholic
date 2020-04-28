import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Review } from 'src/app/review';
import {ReviewService} from 'src/app/review.service';

@Component({
  selector: 'app-create-new-review',
  templateUrl: './create-new-review.page.html',
  styleUrls: ['./create-new-review.page.scss'],
})
export class CreateNewReviewPage implements OnInit {

  listingId: number;
  orderId : number;

  submitted: boolean;
  newReview: Review;
  rating: number;
  description: string;
  picture: string;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;
  errorRatingOutOfBounds: string;
  

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private reviewService: ReviewService) { 
        console.log("Calling create new review constructor**********")
        this.submitted = false;
        this.newReview = new Review();
        this.resultError = false;
        this.resultSuccess = false;
  }

  ngOnInit() {
    this.listingId = parseInt(this.activatedRoute.snapshot.paramMap.get('listingId'));
    this.orderId = parseInt(this.activatedRoute.snapshot.paramMap.get('orderId'));
    console.log("called create review method with listing id: " + this.listingId);
  }

  create(createReviewForm: NgForm) {
    this.submitted = true;

    let reviewPictures: string[] = new Array();
    if (this.picture != null) {
      console.log(this.picture);
      reviewPictures.push(this.picture);
    }

    console.log(reviewPictures[0]);

    if (createReviewForm.valid) {
        this.reviewService.createReview(this.newReview, this.rating, this.listingId, this.description, 
          reviewPictures).subscribe(
            response => {
              let newReviewId: number = response.reviewId;
              this.resultError = false;
              this.resultSuccess = true;
              this.message = "New review " + newReviewId + " created successfully!"

              createReviewForm.reset();
              this.reset();
            },
            error => {
              this.resultError = true;
              this.resultSuccess = false;
              this.message = "An error has occured while creating the new review: " + error;
              console.log('********** CreateNewReviewPage.ts: ' + error);
            }
          )

    }
  }

  changeRating(change) {
    if(this.rating > 5) {
      this.errorRatingOutOfBounds = "Rating cannot go beyond 5";
    } else if (this.rating < 1) {
      this.errorRatingOutOfBounds = "Rating cannot go below 1"
    } else {
      this.rating += change;
      this.errorRatingOutOfBounds = null;
    }
  }

  clear() {
    this.submitted = false;
		this.newReview = new Review();
  }

  private reset() {
    this.newReview  = new Review();
    this.submitted = false;
  }

  back() {
    this.router.navigate(["/viewOrderDetails/" + this.orderId]);
  }

}
