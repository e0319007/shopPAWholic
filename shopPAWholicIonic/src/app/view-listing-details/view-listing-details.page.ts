import { Component, OnInit } from '@angular/core';
import { Listing } from '../listing';
import { Router, ActivatedRoute } from '@angular/router';
import { ListingService } from '../listing.service';
import { AlertController } from '@ionic/angular';
import { UtilityService } from '../utility.service';
import { CartService } from '../cart.service';
import { CartItem } from '../cart-item';

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
  isCustomer: boolean;

  quantityToAddToCart: number;
  errorQuantityToAddExceeded: string;
  addMessage: string;

  constructor(private router: Router, private utilityService: UtilityService, private cartService: CartService, private activeRoute: ActivatedRoute, private listingService: ListingService, private alertController: AlertController) {
    this.retrieveListingError = false;
    this.error = false;
    this.resultSuccess = false;
    this.quantityToAddToCart = 0;
  }

  ngOnInit() {
    this.listingId = parseInt(this.activeRoute.snapshot.paramMap.get('listingId'));
    console.log("called view listing method with listing id: " + this.listingId);
    this.refreshListing();
    this.isCustomer = this.utilityService.isCustomer();

    //deletelater
    this.cartService.initialiseCart();
    console.log("pass init call***");
  }

  refreshListing() {
    this.listingService.retrieveListingById(this.listingId).subscribe(
      response => {
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
    this.router.navigate(["/sellerOperation/updateListing/" + this.listingId]);
  }

  async deleteListing() {
    this.router.navigate(["/sellerOperation/deleteListing/" + this.listingId]);
  }

  addToCart() {
    if(this.quantityToAddToCart <= 0) {
      this.addMessage = "Cannot add items of less or equals 0";
      return;
    }
    this.addMessage = "Item added!";
    console.log("adding to cart**********");
    this.addListingToCart(new CartItem(this.listingToView, this.quantityToAddToCart));
  }

  addListingToCart(cartItem: CartItem) {
    console.log("addListingToCart cart called")
    let cartItems: CartItem[] = JSON.parse(sessionStorage.getItem('cartItems'));
    
    let exist: boolean = false;
    for (let c of cartItems) {
      console.log("*** in loop :" + c.listing.name + " quantity: " + c.quantity)
      if(c.listing.name == cartItem.listing.name) {
        console.log("*** in if: " + c.listing.name + " original qty: " + c.quantity)
        c.quantity += cartItem.quantity;
        console.log("*** in if: " + c.listing.name + " new qty: " + c.quantity)
        exist = true;
        break;
      }
    }
    if (!exist) {
      cartItems.push(cartItem);
    }

    for (let c of cartItems) {
      console.log("*** in after loop :" + c.listing.name + " quantity: " + c.quantity)
    }

    console.log("current number of items in cart: " + cartItems.length);
    sessionStorage.setItem('cartItems', JSON.stringify(cartItems));
  }

  changeQty(change) {
    if(this.quantityToAddToCart + change > this.listingToView.quantityOnHand) {
      this.errorQuantityToAddExceeded = "Insufficient quantity!";
      this.addMessage = null;
    } else {
      this.quantityToAddToCart += change;
      this.errorQuantityToAddExceeded = null;
    }
  }

  back() {
    this.router.navigate(["/viewAllListings"]);
  }
}
