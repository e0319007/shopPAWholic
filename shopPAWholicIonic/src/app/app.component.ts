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
      icon: 'basket'
    },
    {
      title: 'View My Listings',
      url: 'sellerOperation/viewMyListings',
      icon: 'list-circle'
    },
    {
      title: 'Create New Listing',
      url: 'sellerOperation/createNewListing',
      icon: 'add-circle'
    },
    {
      title: 'View Orders',
      url: '/viewAllOrders',
      icon: 'clipboard'
    },
    {
      title: 'Cart',
      url: '/customerOperation/viewCart',
      icon: 'cart'
    },
    {
      title: 'Create Advertisement',
      url: '/sellerOperation/createNewAdvertisement',
      icon: 'cloud-upload'
    },
    {
      title: 'View All Advertisements',
      url: '/viewAllAdvertisements',
      icon: 'phone-portrait'
    },
    {
      title: 'View Billing',
      url: '/sellerOperation/viewBillingDetails',
      icon: 'reader'
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
    private utilityService: UtilityService
  ) {
    this.initializeApp();
    this.updateMainMenu();
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
    this.updateMainMenu();
  }

  updateMainMenu()
	{
    //if is login
		if(this.utilityService.getIsLogin())
		{

		if(this.utilityService.isCustomer()) {

        this.appPages  = [
          {
            title: 'Home',
            url: '/home',
            icon: 'home'
          },
         
          {
             title: 'View All Listings',
             url: '/viewAllListings',
             icon: 'basket'
           },
           {
             title: 'View Orders',
             url: '/viewAllOrders',
             icon: 'clipboard'
           },
           {
             title: 'Cart',
             url: '/customerOperation/viewCart',
             icon: 'cart'
           },
           {
             title: 'View All Advertisements',
             url: '/viewAllAdvertisements',
             icon: 'phone-portrait'
           },
           {
            title: 'Logout',
            url: '/login',
            icon: 'exit'
          },
          ]
      } else {
        this.appPages  = [
          {
            title: 'Home',
            url: '/home',
            icon: 'home'
          },
          {
             title: 'View All Listings',
             url: '/viewAllListings',
             icon: 'basket'
           },
           {
             title: 'View My Listings',
             url: 'sellerOperation/viewMyListings',
             icon: 'list-circle'
           },
           {
             title: 'Create New Listing',
             url: 'sellerOperation/createNewListing',
             icon: 'add-circle'
           },
           {
             title: 'View Orders',
             url: '/viewAllOrders',
             icon: 'clipboard'
           },
           {
             title: 'Create Advertisement',
             url: '/sellerOperation/createNewAdvertisement',
             icon: 'cloud-upload'
           },
           {
             title: 'View All Advertisements',
             url: '/viewAllAdvertisements',
             icon: 'phone-portrait'
           },
           {
             title: 'View Billing',
             url: '/sellerOperation/viewBillingDetails',
             icon: 'reader'
           },
           {
            title: 'Logout',
            url: '/login',
            icon: 'exit'
          },
        ];
      }
      //is not logged in
		} else {
      this.appPages  = [
				{
					title: 'Home',
					url: '/home',
					icon: 'home'
        },
        {
          title: 'View All Listings',
          url: '/viewAllListings',
          icon: 'arrow-forward'
        }
      ];
    }
	}

}