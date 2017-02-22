import { Injectable } from '@angular/core';

import { Survey,Aptitude, Behavior } from './survey-model';

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

  }
}
