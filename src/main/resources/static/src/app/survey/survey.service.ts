import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Survey } from './survey.model';
import { Aptitude, Behavior } from '../aptitude/index';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { LocalStorageService } from 'angular-2-local-storage';

// Enviroment variables
import { environment } from '../../environments/environment';

/*
* Service that provides survey variables to be stored
*/

@Injectable()
export class SurveyService {

  private _surveyUrl = environment.apiURL;
  survey: Survey;
  oneSurvey: boolean;
  competence = 'TESTING';
  evaluator: string;
  role: string;

  constructor(private _http: Http) {
    this.survey = new Survey();
    this._surveyUrl += '/survey';
    this.oneSurvey = (localStorage.getItem('typeOfSurvey') === 'true');
    this.competence = localStorage.getItem('competence');
  }

  startSurvey(survey) {
    this.survey.evaluator = survey.evaluator;
    this.survey.role = survey.role;
    this.survey.evaluated = survey.evaluated;
    this.survey.aptitudes = new Array<Aptitude>();
    console.log(this.survey);
  }

  saveSurvey(surveyToSave: Survey): Observable<Survey> {
    console.log(this._surveyUrl);
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });
    return this._http.post(this._surveyUrl, { surveyToSave }, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    const body = res.json();
    return body.data || {};
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    return Observable.throw(errMsg);
  }


}
