import { Component, OnInit, ViewChild} from '@angular/core';
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


  password : string;
  email: string;

  sliderOne: any;

  constructor(private router: Router, 
              private activatedRoute: ActivatedRoute,  
              private utilityService: UtilityService, 
              private cartService: CartService,
              private userService: UserService){

                  // slidesItems: [
                  //   {image: '../assets/pictures/petexpo.jpg'},
                  //   {image: '../assets/pictures/aquazone.jpg'},
                  //   {image: '../assets/pictures/uncaged.jpg'}
                  // ]

              }

  ngOnInit() {
    this.email = this.utilityService.getEmail();
    this.password = this.utilityService.getPassword();
  }


  sliderConfig = {
    initialSlide: 0,
    slidesPerView: 1,
    loop: true,
    autoplay:true,
    speed: 1000
  };
  

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
