import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class PasswordService {
  private passwordUrl = environment.apiURL; // this gets the url according to the environment
  constructor(private http: Http) {
    this.passwordUrl += '/password/change';
    // remember that the get petition is for the view and the post is for the authentication
  }

  postPasswordRequest(password: any) {// this methods makes the post to the backend endpoint
    const headers = new Headers({ 'Content-Type': 'application/json', 'X-Requested-With': 'XMLHttpRequest' });
    const options = new RequestOptions({ headers: headers });
    const body = password; // the password was stringyfied previously on the component
    return this.http.post(this.passwordUrl, body, options)
      .catch(e => this.handleError(e)); // error handling

  }



  private handleError(error: Response | any) {// here we handle any error that could occur
    const errMsg: string = error.message;
    return Observable.throw(errMsg);
    // throws the error message in case we want to display it to the user (for security reasons we do not want to)
  }

}
