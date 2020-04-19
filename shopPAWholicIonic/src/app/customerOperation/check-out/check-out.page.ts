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
import { OrderEntityService } from 'src/app/order-entity.service';

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

  deliveryCost: number;
  
  constructor(private cartService: CartService,
              private router: Router,
              private deliveryDetailService: DeliveryDetailService,
              private orderService: OrderEntityService) 
  {
    this.deliveryMethods = new Array();
    this.totalPrice = 0 ;
    this.deliveryCost = 0;
    
    this.deliveryMethods.push(DeliveryMethod.SingpostRegular);
    this.deliveryMethods.push(DeliveryMethod.SingpostRegistered);
    this.deliveryMethods.push(DeliveryMethod.Ninjavan);
    this.deliveryMethods.push(DeliveryMethod.ParknParcel);
    this.deliveryMethods.push(DeliveryMethod.Qxpress);
    
  }

  ngOnInit() {
    this.ionViewWillEnter();
  }

  ionViewDidEnter(){
   this.ionViewWillEnter();
  }

  ionViewWillEnter(){
    this.cartItems = JSON.parse(sessionStorage.getItem('cartItems'));
    console.log("cartItems: " + this.cartItems.length);
    this.calculateTotalPrice(this.cartItems);
    console.log("this total price: " + this.totalPrice);
  }

  calculateTotalPrice(cartItems : CartItem[]) {
    this.totalPrice = 0;
    for (let item of cartItems) {
      this.totalPrice += item.quantity * item.listing.unitPrice;
    }
  }

  tempCost: number;
  initialiseDeliveryMethod() {
    let temp: string = this.chosenDeliveryMethod;
    if (temp == "SINGPOST_REGULAR") { 
      this.deliveryDetail.deliveryMethod = DeliveryMethod.SingpostRegular;
      this.tempCost = 1.50;
    }
    else if (temp == "SINGPOST_REGISTERED") {
      this.deliveryDetail.deliveryMethod = DeliveryMethod.SingpostRegistered;
      this.tempCost = 3.50;
    }
    else if (temp == "NINJAVAN") {
      this.deliveryDetail.deliveryMethod = DeliveryMethod.Ninjavan;
      this.tempCost = 4;
    }
    else if (temp == "PARKNPARCEL") {
      this.deliveryDetail.deliveryMethod = DeliveryMethod.ParknParcel;
      this.tempCost = 5.50;
    }
    else if (temp == "QXPRESS") {
      this.deliveryDetail.deliveryMethod = DeliveryMethod.Qxpress;
      this.tempCost = 5;
    }
  }

  checkDelivery() {
    this.initialiseDeliveryMethod();
  }

  checkout(checkoutForm: NgForm) {
    console.log("*****checking out in checkout page.ts")
    console.log("***** this delivery method chosen: " + this.chosenDeliveryMethod);
    this.initialiseDeliveryMethod();
    //this.deliveryDetail.deliveryMethod = this.deliveryMethods[parseInt(this.stringdeliveryMethod)];
    console.log("Reflected delivery method: " + this.deliveryDetail.deliveryMethod)
    if(checkoutForm.valid) {
 
      //initialise total number of sellers
      let sellerList: Seller[] = new Array();
      for (let i of this.cartItems){
        let added = false;
        for (let seller of sellerList) {
          if(seller.userId == i.listing.seller.userId) {
            sellerList.push(seller);
            added = true
          }
        }
        if(!added) {
          sellerList.push(i.listing.seller);
        }
      }
      console.log("theres " + sellerList.length + " number of sellers when checking out")
      this.deliveryCost = this.tempCost * sellerList.length;
      //mapping sellers to list of listings
      let map = new Map<Seller, Listing[]>();
      for (let seller of sellerList) 
        map.set(seller, new Array);
      
      for (let item of this.cartItems) {
        for (let seller of sellerList) {
          if (seller.email == item.listing.seller.email) {
            for (var i = 0; i < item.quantity; i++) {
              map.get(seller).push(item.listing);
            }
          }
        }
      }
      //create orders for individual sellers
      for (let seller of sellerList) {
        this.createNewDelivery(map.get(seller));
      } 

      this.router.navigate(["/viewAllOrders"])
        
    } 
  }  

  createNewDelivery(listings: Listing[]) {
    let deliveryDetailCreated: DeliveryDetail = new DeliveryDetail();
    this.deliveryDetailService.createDeliveryDetail(this.deliveryDetail).subscribe(
      response => {
        deliveryDetailCreated = response.deliveryDetail;
        //delivery detail created
        console.log("*****delivery detail created")
        
        this.createOrder(deliveryDetailCreated, listings);

        this.router.navigate(["/customerOperation/createOrderSuccessPage"])
      },
      error => {
        console.log("error while creating delivery detail : " + error);
      }
    );
      
  }

  back() {
    this.router.navigate(["/customerOperation/viewCart"]);
  }

  createOrder(deliveryDetail : DeliveryDetail, listings: Listing[]) {
    //set price of order
    let priceOfOrder: number = this.tempCost;
    for (let l of listings) {
      priceOfOrder += l.unitPrice;
    }
    //initialising order listing and price
    let orderEntity: OrderEntity = new OrderEntity();
    orderEntity.listings = listings;
    console.log("wait to create order for seller : " + listings[0].seller.name + " with listing length: " + orderEntity.listings.length);
    orderEntity.totalPrice = priceOfOrder;
    //create order
    console.log("&&&&listing length : " + listings.length)
    this.orderService.createOrder(deliveryDetail, this.billingDetail.creditCardDetail, orderEntity, listings).subscribe(
      response => {
        console.log("order created")
        console.log("orderId: " + response.orderEntityId);
        this.cartService.afterCheckoutInitCart();
      },
      error => {
        console.log("Error occured : " + error);
      }
    )
  }

  

}
