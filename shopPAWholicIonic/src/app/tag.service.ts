import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UtilityService } from './utility.service';
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Tag } from './tag';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class TagService {

  baseUrl: string;

  constructor(private httpClient: HttpClient, private utilityService: UtilityService) {
    this.baseUrl = utilityService.getRootPath() + 'Tag';
  }

  retrieveAllTags(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllTags").pipe(
      catchError(this.handleError)
    );
  }

  createNewTag(tag: Tag) {
    let tagCreateNewReq = {
      "tag": tag,
      "email": this.utilityService.getEmail(),
      "password": this.utilityService.getPassword(),
    }

    return this.httpClient.put<any>(this.baseUrl, tagCreateNewReq, httpOptions).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string;
    if(error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occured: " + error.error.message;
    } else {
      errorMessage = "A HTTP error has occured: " + `HTTP ${error.status}: ${error.error.status}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
