import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class LoginService {
    private loginUrl = environment.apiURL; // this gets the url according to the environment
    constructor(private http: Http) {
        this.loginUrl += '/login';
        // remember that the get petition is for the view and the post is for the authentication
    }

    login(username: String, password: String) { // custom class, may be empty now

        const headers = new Headers({// this is the way HTTP basic authentication is done
            'Authorization': 'Basic ' + btoa(username + ':' + password),
            'X-Requested-With': 'XMLHttpRequest' // to suppress 401 browser popup
        });

        const options = new RequestOptions({// this fills the header of the request
            headers: headers
        });

        return this
            .http
            .post(this.loginUrl, {}, options)// this does the post per se
            .catch(e => this.handleError(e)); // handle 401 error - bad credentials
    }

    private handleError(error: Response | any) {// here we handle any error that could occur
        const errMsg: string = error.message;// gets the message from the response error 
        return Observable.throw(errMsg);
        // throws the error message in case we want to display it to the user (for security reasons we do not want to)
    }

}
