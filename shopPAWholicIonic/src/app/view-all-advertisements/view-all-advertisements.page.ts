import { Component, OnInit } from '@angular/core';
import { Advertisement } from '../advertisement';
import { Router } from '@angular/router';
import { AdvertisementService } from '../advertisement.service';

@Component({
  selector: 'app-view-all-advertisements',
  templateUrl: './view-all-advertisements.page.html',
  styleUrls: ['./view-all-advertisements.page.scss'],
})
export class ViewAllAdvertisementsPage implements OnInit {

  advertisements:Advertisement[] = new Array();

  constructor(private router:Router, private advertisementService: AdvertisementService) { }

  ngOnInit() {
    this.refreshAds();
  }

  ionViewWillEnter() {
    this.refreshAds();
  }

  refreshAds() {
    this.advertisementService.retrieveAllAdvertisements().subscribe(
      response => {
        this.advertisements = response.advertisement;
        console.log("Advertisement length: " + this.advertisements.length);
      },
      error => {
        console.log('********* ViewAllAdvertisementsPage.ts: ' + error);
      }
    )
  }


}
