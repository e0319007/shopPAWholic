import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { UtilityService } from '../utility.service';
import { UserService } from '../user.service';
import { User } from '../user';
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
  
  constructor(private router: Router, public utilityService: UtilityService, private userService: UserService) { 
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
            let user : User = response.User;

            if (response.User instanceof Customer) {
              this.utilityService.isCustomer();
            } else if (response.User instanceof Seller) {
              this.utilityService.isSeller()
            }

            if(user != null){
              this.utilityService.setIsLogin(true);
              this.utilityService.setCurrentUser(user);					
              this.loginError = false;					
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
        //throw userloginform not valid error??
      }
  }
	
	userLogout(): void
	{
		this.utilityService.setIsLogin(false);
		this.utilityService.setCurrentUser(null);		
  }
  
  back(){
		this.router.navigate(["/home"]);
	}

}
