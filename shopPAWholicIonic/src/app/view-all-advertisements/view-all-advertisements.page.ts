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
  hasAds : boolean = true;

  constructor(private router:Router, private advertisementService: AdvertisementService) { }

  ngOnInit() {
    console.log("View All Advertisements page");
    this.refreshAds();
  }

  ionViewWillEnter() {
    this.refreshAds();
  }

  refreshAds() {
    console.log("*********DEBUG 2")
    this.advertisementService.retrieveAllAdvertisements().subscribe(
      response => {
        console.log("*********DEBUG 1")
        this.advertisements = response.advertisement;
        if (this.advertisements.length == 0) {
          this.hasAds = false;
        } else {
          this.hasAds = true;
        }
      },
      error => {
        console.log('********* ViewAllAdvertisementsPage.ts: ' + error);
      }
    )
  }

  parseDate(d: Date) {
    return d.toString().replace('[UTC]','');
  }



}

