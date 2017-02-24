import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { SurveyService } from './survey.service';

import{
 Aptitude,
 AptitudeService,
 Behavior
} from '../aptitude/index'

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css', '../app.component.css']
})
export class SurveyComponent implements OnInit {

  public next: string;
  errorMessage: string;

  currentLanguage: string;

  behaviors: Behavior[];

  constructor(private surveyService: SurveyService, private _aptitudeService: AptitudeService, private translate: TranslateService, private route: ActivatedRoute) { 
     this.currentLanguage = translate.currentLang;
  }

  ngOnInit() {
    this.next = 'logros';
    
    console.log("Aptitude Id to get Behaviors ", this.route.snapshot.params['id']);
    
    this._aptitudeService.getBehaviors(this.route.snapshot.params['id']).subscribe(behaviors => this.behaviors = behaviors, error => this.errorMessage = <any>error)
  }

/*
* Hardcoded values
*/
  nextAptitude(){
    if(this.next !== ""){
       $("#openess").addClass('active');
    }else{
      $(".active").next().addClass('active');
    }
  }

  save(model: any, isValid: boolean) {
        // check if model is valid
        // if valid, call API to save customer
        console.log(model, isValid);
    }
  
  saveSurvey(survey){
    if (!survey) { return; }
    this.surveyService.saveSurvey(survey)
                     .subscribe(
                       survey  => this.surveyService.survey = survey,
                       error =>  this.errorMessage = <any>error);
  }
  

}
