import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
 
@Injectable()
export class LoginService {
    private loginUrl = environment.apiURL;
    constructor(private http: Http) { 
        this.loginUrl += '/login';
    }
 
    login (username: String, password: String){ // custom class, may be empty now

    let headers = new Headers({ 
          'Authorization': 'Basic ' + btoa(username + ':' + password),
          'X-Requested-With': 'XMLHttpRequest' // to suppress 401 browser popup
    });

    let options = new RequestOptions({ 
           headers: headers 
    });

    return this
              .http
              .post(this.loginUrl, {}, options)
              .catch(e => this.handleError(e)); // handle 401 error - bad credentials
}
 
 private handleError(error: Response | any) {
    let errMsg: string;
    let errorMsg = error.message
    return Observable.throw(errMsg);
  }
    
}