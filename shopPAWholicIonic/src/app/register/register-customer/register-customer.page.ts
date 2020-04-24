import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import {CustomerService} from 'src/app/customer.service';
import { Customer } from 'src/app/customer';
import { UtilityService } from 'src/app/utility.service';
import { CartService } from 'src/app/cart.service';

@Component({
  selector: 'app-register-customer',
  templateUrl: './register-customer.page.html',
  styleUrls: ['./register-customer.page.scss'],
})
export class RegisterCustomerPage implements OnInit {

  submitted: boolean;
  name: string;
  email: string;
  password: string;
  contactNumber: string;
  newCustomer: Customer;
  resultSuccess: boolean;
  resultError: boolean;
  accCreatedDate : Date;
  message: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private customerService: CustomerService,
    private utilityService: UtilityService,
    private cartService : CartService) {
      this.submitted = false;
      this.newCustomer = new Customer();
      this.resultSuccess = false;
		  this.resultError = false;
      this.accCreatedDate = new Date();
     }

  ngOnInit() {
  }

  clear()
	{
    this.email = "";
    this.password = "";
    this.contactNumber = "";
    this.name = "";
		this.submitted = false;
		this.newCustomer = new Customer();
  }
  
  private reset() {
    this.newCustomer  = new Customer();
    this.submitted = false;
  }
  
  customerRegister(customerRegisterForm: NgForm) {
    this.submitted = true;

    console.log(this.accCreatedDate);

    if (customerRegisterForm.valid) {
        this.utilityService.setEmail(this.email);
        this.utilityService.setPassword(this.password);
        this.customerService.customerRegister(this.newCustomer, this.name, this.email, this.password, this.contactNumber, this.accCreatedDate).subscribe(
          response => {
            let newCustomerId: number = response.customerId;
            this.resultError = false;
            this.resultSuccess = true;
            this.message = "New customer " + newCustomerId + " created successfully!"
            this.utilityService.setIsLogin(true);
            this.utilityService.setIsCustomer;
            this.initialiseCart();
            
            customerRegisterForm.reset();
            this.reset;
          },
          error => {
            this.resultError = true;
            this.resultSuccess = false;
            this.message = "An error has occured while creating the new customer: " + error;
            console.log('********** RegisterCustomerPage.ts: ' + error);
          }
        )

    }

  }

  initialiseCart() {
    this.cartService.initialiseCart();
  }



}
