import { Component, OnInit } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import {UtilityService} from './utility.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent implements OnInit {
  public selectedIndex = 0;
  public appPages = [
   {
     title: 'Home',
     url: '/home',
     icon: 'home'
   },
   {
      title: 'View All Listings',
      url: '/viewAllListings',
      icon: 'arrow-forward'
    },
    {
      title: 'View My Listings',
      url: 'sellerOperation/viewMyListings',
      icon: 'arrow-forward'
    },
    {
      title: 'Create New Listing',
      url: 'sellerOperation/createNewListing',
      icon: ''
    },
    {
      title: 'View Orders',
      url: '/viewAllOrders',
      icon: ''
    },
    {
      title: 'Cart',
      url: '/customerOperation/viewCart',
      icon: ''
    },
    {
      title: 'Advertisements',
      url: '/viewAdvertisements',
      icon: ''
    },
    {
      title: 'View Reviews',
      url: '/viewReviews',
      icon: ''
    },
    {
      title: 'View Billing',
      url: '/sellerOperation/viewBillingDetails',
      icon: ''
    },
    {
      title: 'Logout',
      url: '/login',
  		icon: 'exit'
    }
  ];
  public labels = ['Family', 'Friends', 'Notes', 'Work', 'Travel', 'Reminders'];

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  ngOnInit() {
    const path = window.location.pathname.split('folder/')[1];
    if (path !== undefined) {
      this.selectedIndex = this.appPages.findIndex(page => page.title.toLowerCase() === path.toLowerCase());
    }
  }

  // updateMainMenu()
	// {
  //   //if is login
	// 	if(this.utilityService.getIsLogin())
	// 	{
	// 		this.appPages  = [
	// 			{
	// 				title: 'Home',
	// 				url: '/home',
	// 				icon: 'home'
	// 			},
	// 			{
	// 				title: 'Logout',
	// 				url: '/login',
	// 				icon: 'exit'
	// 			}
  //     ];
  //     //is not logged in
	// 	} else {
  //     this.appPages  = [
	// 			{
	// 				title: 'Home',
	// 				url: '/home',
	// 				icon: 'home'
  //       },
  //       {
  //         title: 'View All Listings',
  //         url: '/viewAllListings',
  //         icon: 'arrow-forward'
  //       }
  //     ];
  //   }
	// }

}
