import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { IEmployee } from './employee.model';

@Injectable()
export class EmployeeService {
  private _employeeUrl = 'http://ec2-52-33-244-144.us-west-2.compute.amazonaws.com:8080/person'  //'https://anypoint.mulesoft.com/apiplatform/proxy/https://mocksvc.mulesoft.com/mocks/d4804468-6192-482e-a2eb-53dca0d66495/person';

  constructor(private _http: Http) { }

  /*
  * Method to get all employees from REST response
  * Return type: Observable
  */
  getEmployees(): Observable<IEmployee[]> {
        return this._http.get(this._employeeUrl)
            .map((response: Response) => <IEmployee[]> response.json())
            .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

  /*
  * Method to get one employee from REST response given the ID
  * Return type: Observable
  */
  getEmployee(id: number): Observable<IEmployee> {
        return this.getEmployees()
            .map((employee: IEmployee[]) => employee.find(p => p.employeeId === id));
    }

  /*
  * Method to handle error and log it into console
  */
  private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }

}
