import { Component, OnInit } from '@angular/core';

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

  behaviors: Behavior[];

  constructor(private surveyService: SurveyService, private _aptitudeService: AptitudeService) { }

  ngOnInit() {
    this.next = 'logros';
    
    this._aptitudeService.getBehaviors('1').subscribe(behaviors => this.behaviors = behaviors, error => this.errorMessage = <any>error)
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

}
