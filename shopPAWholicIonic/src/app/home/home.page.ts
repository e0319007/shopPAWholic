import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service'
import { CartService } from '../cart.service';
import { UtilityService } from '../utility.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(private router: Router, 
              private activatedRoute: ActivatedRoute,  
              private utilityService: UtilityService, 
              private cartService: CartService,
              private userService: UserService){}

  password : string;
  email: string;

  ngOnInit() {
    this.email = this.utilityService.getEmail();
    this.password = this.utilityService.getPassword();
  }

  goLogin() {
    this.router.navigate(["/login"]);
  }

  logout(){
    this.userService.userLogout();
  }
    
  initialiseCart() {
    this.cartService.initialiseCart();
  }

}
