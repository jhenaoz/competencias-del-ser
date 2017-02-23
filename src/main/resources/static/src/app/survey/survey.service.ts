import { Injectable } from '@angular/core';

import { Survey } from './survey.model';
import { Aptitude, Behavior } from '../aptitude/index';

/*
* Service that provides survey variables to be stored 
*/

@Injectable()
export class SurveyService {

  survey: Survey
  oneSurvey: boolean

  constructor() { this.survey = new Survey() }

  startSurvey(survey){
    this.survey.evaluator = survey.evaluator
    this.survey.role = survey.role
    this.survey.evaluated = survey.evaluated
    this.testPrint()
  }

    testPrint(){
      console.log(this.survey.evaluator)
      console.log(this.survey.role)
      console.log(this.survey.evaluated)
      console.log("asdasd ");

  }
}
