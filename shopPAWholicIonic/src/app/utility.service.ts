import { Injectable } from '@angular/core';

import { Platform } from '@ionic/angular';
import { User } from './user';
import { Customer } from './customer';
import { Seller } from './seller';


@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(private platform:Platform) { }

  getRootPath(): string
  {
    console.log('this.platform.is("hybrid"): ' + this.platform.is('hybrid'));
    
    if(this.platform.is('hybrid'))
    {
      return "http://192.168.137.1:8080/shopPAWholic-rws/Resources/";
    }
    else
    {
      return "/api/";
    }
  }

  getIsLogin() : boolean {
    if(sessionStorage.isLogin == "true") {
      return true;
    } else {
      return false;
    }
  }

  setIsLogin(isLogin : boolean) : void {
    sessionStorage.isLogin = isLogin;
  }

  getCurrentUser() : User {
    return JSON.parse(sessionStorage.currentUser);
  }

  setCurrentUser(user: User) {
    sessionStorage.currentUser = JSON.stringify(user);
  }

  getEmail(): string {
    this.setEmail("dummy");
    return sessionStorage.email;
  }

  setEmail(email: string) {
     let e: string = "customerOne@email.com";
     sessionStorage.email = e;
    //sessionStorage.email = email;
  }

  getPassword(): string {
    this.setPassword("dummy");
    return sessionStorage.password;
  }

  setPassword(password: string) {
    let p: string = "password";
    sessionStorage.password = p;
    //sessionStorage.password = password;
  }

  isCustomer(): boolean {
    return true;
   /**  let currentUser = this.getCurrentUser(); 
    if (currentUser instanceof Customer) {
      return true;
    } else {
      return false;
    } */
  }

  isSeller(): boolean {
    let currentUser = this.getCurrentUser();
    if (currentUser instanceof Seller) {
      return true;
    } else {
      return false;
    }
  }

  checkAccessRight(path) : boolean {
    console.log("***************PATH: " + path);
    if(this.getIsLogin()) {
      let user: User;
      user = this.getCurrentUser();
      if(user instanceof Customer) {
        //if condition for paths
        return true;
      } else if (user instanceof Seller) {
        //if conditions for paths
        return true;
      }
    } 
  }

}
