import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { Behavior } from './behavior.model';

@Injectable()
export class AptitudeService {
  private _aptitudeUrl = 'https://anypoint.mulesoft.com/apiplatform/proxy/https://mocksvc.mulesoft.com/mocks/9626f05b-5baf-4721-8077-9a79ea6e929d/aptitude/'

  constructor(private _http: Http) { }

  /*
  * Method to get all behaviors from REST response
  * Return type: Observable
  */
  getBehaviors(aptitudeId: string): Observable<Behavior[]> {

        return this._http.get(this._aptitudeUrl + aptitudeId + '/behavior')
            .map((response: Response) => <Behavior[]> response.json())
            .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

  
  /*
  * Method to handle error and log it into console
  */
  private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

}
