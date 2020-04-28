import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { UtilityService } from '../utility.service';
import { UserService } from '../user.service';
import { User } from '../user';
import { CartService } from '../cart.service';
import { Customer } from '../customer';
import { Seller } from '../seller';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  submitted: boolean;
	email: string;
	password: string;
	loginError: boolean;
  errorMessage: string;
  
  constructor(private router: Router, 
              public utilityService: UtilityService, 
              private userService: UserService,
              private cartService: CartService) { 
      this.submitted = false;
    }

  ngOnInit() {
  }

  clear()	{
		this.email = "";
		this.password = "";
  }
  
  userLogin(userLoginForm: NgForm) {
      this.submitted = true;

      if (userLoginForm.valid) {
        this.utilityService.setEmail(this.email);
        this.utilityService.setPassword(this.password);

        this.userService.UserLogin(this.email, this.password).subscribe(
          response => {
            let user : User = response.user;
            //got problems with the instanceof im not sure if i need to add a restful method :///
            console.log('********** DEBUG 1')
            console.log('********** DEBUG ' + response.user.verified)           

            if(response.user.verified == null)
            {
              console.log('********** DEBUG 3')
              this.utilityService.setIsCustomer(true);     
     
            }
            else 
            {
              console.log('********** DEBUG 2')
              this.utilityService.setIsSeller(true);
            }

            if(user != null){
              this.utilityService.setIsLogin(true);
              this.utilityService.setCurrentUser(user);					
              this.loginError = false;	
              if (this.utilityService.isCustomer()) {
                console.log("is customer");
                 this.initialiseCart();   
              } 
              console.log(this.utilityService.getIsLogin())				
            } else {
              this.loginError = true;
            }
          },
          error => {
            this.loginError = true;
            this.errorMessage = error
          }      
        );

      } else {
        this.loginError = true; //some error msg thing
      }
  }
	
	userLogout(): void
	{
    // this.cartService.saveCartToDatabase();
    this.utilityService.setIsLogin(false);
    this.utilityService.setCurrentUser(null);		
    this.utilityService.setEmail(null);
    this.utilityService.setPassword(null);
    this.utilityService.setIsCustomer(false);
    this.utilityService.setIsSeller(false);
    console.log(sessionStorage.isLogin);
  }

  initialiseCart() {
    this.cartService.initialiseCart();
  }
  
  back(){
		this.router.navigate(["/home"]);
	}

}
