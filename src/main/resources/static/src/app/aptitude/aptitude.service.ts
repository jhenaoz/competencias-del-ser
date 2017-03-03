import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

// Enviroment variables
import { environment } from '../../environments/environment';

import { Behavior } from './behavior.model';

@Injectable()
export class AptitudeService {
  private _aptitudeUrl = environment.apiURL;

  constructor(private _http: Http) {
      this._aptitudeUrl += '/aptitude/';
  }

  /*
  * Method to get all behaviors from REST response
  * Return type: Observable
  */
  getBehaviors(aptitudeId: string): Observable<Behavior[]> {
      // console.log(this._aptitudeUrl);

        return this._http.get(this._aptitudeUrl + aptitudeId + '/behavior')
            .map((response: Response) => <Behavior[]> response.json())
  //          .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

      /*getBehaviorLength(aptitudeId: string): Observable<number> {
         return this._http.get(this._aptitudeUrl + aptitudeId + '/behavior')
            .map((response: Response) => <number> response.json().length)
            .do(data => console.log('Length: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }*/


  /*
  * Method to handle error and log it into console
  */
  private handleError(error: Response) {
        // console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

}
