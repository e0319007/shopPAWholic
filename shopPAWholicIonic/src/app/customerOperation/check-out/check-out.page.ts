import { Component, OnInit } from '@angular/core';
import { DeliveryDetail } from 'src/app/delivery-detail';
import { DeliveryDetailService } from 'src/app/delivery-detail.service';
import { CartService } from 'src/app/cart.service';
import { Router } from '@angular/router';
import { Listing } from 'src/app/listing';
import { Seller } from 'src/app/seller';
import { OrderEntity } from 'src/app/order-entity';
import { CartItem } from 'src/app/cart-item';
import { NgForm } from '@angular/forms';
import { DeliveryMethod } from 'src/app/delivery-method.enum';
import { BillingDetail } from 'src/app/billing-detail';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.page.html',
  styleUrls: ['./check-out.page.scss'],
})
export class CheckOutPage implements OnInit {

  deliveryDetail: DeliveryDetail = new DeliveryDetail();
  deliveryDetailCreated: DeliveryDetail;
  billingDetail: BillingDetail = new BillingDetail();
  totalPrice: number;
  cartItems: CartItem[];
  deliveryMethods: DeliveryMethod[];
  chosenDeliveryMethod: string;

  successMessage: string;
  orderId: number;

  des: string;
  
  constructor(private cartService: CartService,
              private router: Router,
              private deliveryDetailService: DeliveryDetailService) 
  {
    this.deliveryMethods = new Array();
    
    this.deliveryMethods.push(DeliveryMethod.SingpostRegular);
    this.deliveryMethods.push(DeliveryMethod.SingpostRegistered);
    this.deliveryMethods.push(DeliveryMethod.Ninjavan);
    this.deliveryMethods.push(DeliveryMethod.ParknParcel);
    this.deliveryMethods.push(DeliveryMethod.Qxpress);
    
  }

  ngOnInit() {
    this.cartItems = JSON.parse(sessionStorage.getItem('cartItems'));
    console.log("cartItems: " + this.cartItems.length);
    for (let item of this.cartItems) {
      this.totalPrice += item.quantity * item.listing.unitPrice;
    }
  }

  initialiseDeliveryMethod() {
    let temp: string = this.chosenDeliveryMethod;
    if (temp == "SINGPOST_REGULAR") this.deliveryDetail.deliveryMethod = DeliveryMethod.SingpostRegular;
    else if (temp == "SINGPOST_REGISTERED") this.deliveryDetail.deliveryMethod = DeliveryMethod.SingpostRegistered;
    else if (temp == "NINJAVAN") this.deliveryDetail.deliveryMethod = DeliveryMethod.Ninjavan;
    else if (temp == "PARKNPARCEL") this.deliveryDetail.deliveryMethod = DeliveryMethod.ParknParcel;
    else if (temp == "QXPRESS") this.deliveryDetail.deliveryMethod = DeliveryMethod.Qxpress;
  }

  checkout(checkoutForm: NgForm) {
    console.log("*****checking out in checkout page.ts")
    console.log("***** this delivery method chosen: " + this.chosenDeliveryMethod);
    this.initialiseDeliveryMethod();
    //this.deliveryDetail.deliveryMethod = this.deliveryMethods[parseInt(this.stringdeliveryMethod)];
    console.log("Reflected delivery method: " + this.deliveryDetail.deliveryMethod)
    if(checkoutForm.valid) {
      let deliveryDetailCreated: DeliveryDetail = new DeliveryDetail();
      this.deliveryDetailService.createDeliveryDetail(this.deliveryDetail).subscribe(
        response => {
          deliveryDetailCreated = response.deliveryDetail;
          //delivery detail created
          console.log("*****delivery detail created")
          let checkoutListings: Listing[];
          let map = new Map<Seller, Listing[]>();
          let sellerSet = new Set<Seller>();
          for (let i of this.cartItems){
            if (!sellerSet.has(i.listing.seller)) {
              sellerSet.add(i.listing.seller)
            }
          }
          console.log("theres " + sellerSet.size + " number of sellers when checking out")
      
          for (let seller of sellerSet) {
            map.set(seller, new Array);
            for (let item of this.cartItems) {
              if (seller.email == item.listing.seller.email) {
                for (var i = 0; i < item.quantity; i++) {
                  map.get(seller).push(item.listing);
                }
              }
            }
          }
      
          for (let seller of sellerSet) {
            this.createOrder(deliveryDetailCreated, map.get(seller));
          } 
        },
        error => {
          console.log("error while creating delivery detail : " + error);
        }
      )
    } 
  }  

  createOrder(deliveryDetail : DeliveryDetail, listings: Listing[]) {
    let orderEntity: OrderEntity = new OrderEntity();
    orderEntity.listings = listings;
    orderEntity.totalPrice = this.totalPrice;
    this.cartService.createOrder(deliveryDetail, this.billingDetail.creditCardDetail, orderEntity, listings).subscribe(
      response => {
        this.orderId = response.orderEntityId;
        this.successMessage = "Order to " + listings[0].seller.userId + "with ID " + this.orderId + "created successfully!"
      },
      error => {
        console.log("Error occured : " + error);
      }
    )
  }

  

}
