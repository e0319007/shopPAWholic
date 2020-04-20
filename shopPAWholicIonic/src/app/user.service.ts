import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from './user';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'User';
  }

  UserLogin(email: string, password: string): Observable<any>{
    let url: string = this.baseUrl + "/userLogin?email=" + email + "&password=" + password;
    console.log(url);
		return this.httpClient.get<any>(url).pipe(catchError(this.handleError));
  }

  userRegister(user: User, email: string, password: string) {
    let userCreateNewReq = {
      "email" : email,
      "password" : password,
    }
    return this.httpClient.put<any>(this.baseUrl, userCreateNewReq, httpOptions).pipe(catchError(this.handleError));
  }
  
  handleError(error: HttpErrorResponse) {
    let errorMessage: string;
    if(error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error occured: " + error.error.message;
    } else {
      errorMessage = "A HTTP error occured: " + `HTTP ${error.status}: ${error.error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
  
  userLogout(): void
	{
		this.utilityService.setIsLogin(false);
		this.utilityService.setCurrentUser(null);		
  }

}
