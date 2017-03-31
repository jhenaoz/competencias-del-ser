import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { environment } from '../../environments/environment';



@Injectable()
export class AdminService {

    private url = environment.apiURL;

    constructor(private _http: Http) {
    }

    fileRequest(evaluated: string, startDate: string, endDate: string, fileType: String) {
        this.url += '/api/survey/report/';
        this.url += fileType;
        console.log(this.url);


        const params: URLSearchParams = new URLSearchParams();
        params.set('name', evaluated);
        params.set('startdate', startDate);
        params.set('enddate', endDate);

        console.log(params);


        return this._http.get(this.url, {
            search: params
        });
    }
}
