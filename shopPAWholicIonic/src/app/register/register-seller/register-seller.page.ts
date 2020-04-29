import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import {SellerService} from 'src/app/seller.service';
import { Seller } from 'src/app/seller';
import { UtilityService } from 'src/app/utility.service';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-register-seller',
  templateUrl: './register-seller.page.html',
  styleUrls: ['./register-seller.page.scss'],
})
export class RegisterSellerPage implements OnInit {

  submitted: boolean;
  name: string;
  email: string;
  password: string;
  contactNumber: string;
  newSeller: Seller;
  resultSuccess: boolean;
  resultError: boolean;
  accCreatedDate : Date;
  message: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private sellerService: SellerService,
    private utilityService: UtilityService,
    private app: AppComponent) { 
      this.submitted = false;
      this.newSeller = new Seller();
      this.resultSuccess = false;
		  this.resultError = false;
      this.accCreatedDate = new Date();
    }

  ngOnInit() {
    this.app.updateMainMenu();
  }

  refresh(){
    this.app.updateMainMenu();
  }
  

  clear()
	{
    this.email = "";
    this.password = "";
    this.contactNumber = "";
    this.name = "";
		this.submitted = false;
		this.newSeller = new Seller();
  }
    
  private reset() {
    this.newSeller  = new Seller();
    this.submitted = false;
  }

  sellerRegister(sellerRegisterForm: NgForm) {
    this.submitted = true;

    console.log(this.accCreatedDate);

    if (sellerRegisterForm.valid) {
        this.utilityService.setEmail(this.email);
        this.utilityService.setPassword(this.password);
        this.sellerService.sellerRegister(this.newSeller, this.name, this.email, this.password, this.contactNumber, this.accCreatedDate).subscribe(
          response => {
            let newSellerId: number = response.sellerId;
            this.resultError = false;
            this.resultSuccess = true;
            this.message = "New seller " + newSellerId + " created successfully!"
            // this.utilityService.setIsLogin(true);
            // this.utilityService.setIsSeller;
            
            sellerRegisterForm.reset();
            this.reset;
          },
          error => {
            this.resultError = true;
            this.resultSuccess = false;
            this.message = "An error has occured while creating the new customer: " + error;
            console.log('********** RegisterCustomerPage.ts: ' + error);
          }
        )
          this.refresh();
          this.login();
    }

  }

  login() {
    this.router.navigate(["/login"]);
  }

  back() {
    this.router.navigate(["/register"]);
  }

}
