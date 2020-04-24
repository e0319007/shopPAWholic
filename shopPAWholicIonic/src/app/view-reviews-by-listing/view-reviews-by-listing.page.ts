import { Component, OnInit } from '@angular/core';
import { Listing } from '../listing';
import { Router, ActivatedRoute } from '@angular/router';
import { Review } from 'src/app/review';
import { ReviewService} from 'src/app/review.service';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-view-reviews-by-listing',
  templateUrl: './view-reviews-by-listing.page.html',
  styleUrls: ['./view-reviews-by-listing.page.scss'],
})
export class ViewReviewsByListingPage implements OnInit {

  listingId: number;
  reviews: Review[] = new Array();

  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  constructor(private router: Router, private utilityService: UtilityService,  
              private activeRoute: ActivatedRoute, private reviewService: ReviewService) 
            {
              this.error = false;
              this.resultSuccess = false;
            }

  ngOnInit() {
    this.listingId = parseInt(this.activeRoute.snapshot.paramMap.get('listingId'));
    console.log("called view reviews by listing method with listing id: " + this.listingId);
    this.refreshReviews();
    // console.log("picture url: " + this.reviews[1].reviewPictures[0]);
  }

  ionViewWillEnter() {
    this.refreshReviews();
  }

  refreshReviews(){
    this.reviewService.retrieveAllReviewsByListingId(this.listingId).subscribe(
      response => {
        this.reviews = response.reviews;
      }, error => {
        console.log('********* ViewAllReviewsByListing.ts: ' + error);
      }
    )
  }

}
