import { Injectable } from '@angular/core';

import { ISurvey,IAptitude, IBehavior } from './survey-model';

@Injectable()
export class SurveyService {

  survey: ISurvey

  constructor() { }

  startSurvey(survey){
    this.survey.evaluator = survey.evaluator
    this.survey.role = survey.role
    this.survey.evaluated.push(survey.evaluated[0])
    console.log("Funciona el servicio")
    console.log("Funciona el servicio")
    console.log("Funciona el servicio")
  }

    testPrint(){
    console.log(this.survey.evaluator)
    console.log(this.survey.role)
    console.log(this.survey.evaluated)

  }
}
