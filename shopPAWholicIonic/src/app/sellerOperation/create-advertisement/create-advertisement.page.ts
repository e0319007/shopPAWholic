import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Advertisement } from 'src/app/advertisement';
import { AdvertisementService } from 'src/app/advertisement.service';

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.page.html',
  styleUrls: ['./create-advertisement.page.scss'],
})
export class CreateAdvertisementPage implements OnInit {

  submitted: boolean;
  newAdvertisement: Advertisement;

  startDate: Date;
  startYear: number;
  startMth: number;
  startDay: number;
  endDate: Date;
  endYear: number;
  endMth: number;
  endDay: number;
  ccNum: string;
  picture : string;
  url: string;
  description: string;
  price: number;

  resultSuccess: boolean;
  resultError: boolean;
  message: string;

  constructor(private router: Router, 
              private activatedRoute: ActivatedRoute,
              private advertisementService: AdvertisementService) 
  {
    this.submitted = false;
    this.newAdvertisement = new Advertisement();
    this.resultError = false;
    this.resultSuccess = false;
    //putting dummy dates in first then configure in backend
    this.startDate = new Date();
    this.endDate = new Date();
  }

  ngOnInit() {
  }

  create(createAdvertisementForm: NgForm) {

    console.log("start year: " + this.startYear);
    this.startDate.setFullYear(this.startYear);
    this.startDate.setMonth(this.startMth);
    this.startDate.setDate(this.startDay);
    console.log("start: " + this.startDate);
    this.endDate = new Date(this.endYear + "-" + this.endMth + "-" + this.endDay);
    console.log("end: " + this.endDate);

    var diff = Math.abs(this.endDate.getTime() - this.startDate.getTime());
    var diffDays = Math.ceil(diff / (1000 * 3600 * 24)); 

    this.price = diffDays*1;
    console.log("price: " + this.price);

    this.submitted = true;

    console.log("ccNum: " + this.ccNum);

    this.picture = this.newAdvertisement.picture;
    console.log("picture: " + this.picture);

    if (createAdvertisementForm.valid) {

      this.advertisementService.createAdvertisement(this.newAdvertisement, this.startDate, this.startYear, this.startMth,
        this.startDay, this.endDate, this.endYear, this.endMth, this.endDay, this.ccNum, this.picture, 
        this.url, this.description, this.price).subscribe(
          response => {
            let newAdvertisementId: number = response.advertisementId;
            this.resultError = false;
            this.resultSuccess = true;
            this.message = "New Advertisement " + newAdvertisementId + " created successfully!";
          
          createAdvertisementForm.reset();
          this.reset();
        }, 
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occured while creating the new advertisement: " + error;
          console.log('********** CreateAdvertisement.ts: ' + error);
        }
      )

    }

  }

  clear() {
    this.submitted = false;
		this.newAdvertisement = new Advertisement();
  }

  private reset() {
    this.newAdvertisement  = new Advertisement();
    this.submitted = false;
  }


}
