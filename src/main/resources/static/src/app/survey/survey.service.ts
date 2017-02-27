import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Survey } from './survey.model';
import { Aptitude, Behavior } from '../aptitude/index';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

/*
* Service that provides survey variables to be stored
*/

@Injectable()
export class SurveyService {

  private _surveyUrl = '';

  survey: Survey;
  oneSurvey: boolean;

  constructor(private _http: Http) { this.survey = new Survey(); }

  startSurvey(survey) {
    this.survey.evaluator = survey.evaluator;
    this.survey.role = survey.role;
    this.survey.evaluated = survey.evaluated;
    this.testPrint();
  }

  testPrint() {
      console.log(this.survey.evaluator);
      console.log(this.survey.role);
      console.log(this.survey.evaluated);
      console.log('asdasd ');
  }

  saveSurvey(surveyToSave: Survey): Observable<Survey> {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });
    return this._http.post(this._surveyUrl, { surveyToSave }, options)
                    .map(this.extractData)
                    .catch(this.handleError);
  }

  private extractData(res: Response) {
    const body = res.json();
    return body.data || { };
  }

  private handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }


}
