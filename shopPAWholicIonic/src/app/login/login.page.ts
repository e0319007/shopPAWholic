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
            let user : User = response.user;
            //got problems with the instanceof im not sure if i need to add a restful method :///
            if (response.user instanceof Customer) {
              this.utilityService.isCustomer();
            } else if (response.user instanceof Seller) {
              this.utilityService.isSeller()
            }

            if(user != null){
              this.utilityService.setIsLogin(true);
              this.utilityService.setCurrentUser(user);					
              this.loginError = false;	
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
		this.utilityService.setIsLogin(false);
		this.utilityService.setCurrentUser(null);		
  }
  
  back(){
		this.router.navigate(["/home"]);
	}

}
