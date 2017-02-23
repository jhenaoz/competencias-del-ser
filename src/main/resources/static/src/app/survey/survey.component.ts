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

  currentLanguage: string;

  behaviors: Behavior[];

  observation: string;

  constructor(private surveyService: SurveyService, private _aptitudeService: AptitudeService, private translate: TranslateService) { 
     this.currentLanguage = translate.currentLang;
  }

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

  save(model: any, isValid: boolean) {   
       if(this.validateSurvey()){
        $("#radio-alert").addClass("hide");
        $("input:checked").removeAttr("checked");
       }else{
         $("#radio-alert").removeClass("hide");
       }
        for(let i = 1; i <= Object.keys(this.behaviors).length; i++){
          alert($('input[name="radio'+i+'"]:checked').val())
        }
        // alert($('input[name="radio1"]:checked').val());
        // alert($('input[name="radio2"]:checked').val());
        // alert($('input[name="radio3"]:checked').val());
        // alert($('input[name="radio4"]:checked').val());
    }

    validateSurvey(): boolean{
      for(let i = 1; i <= Object.keys(this.behaviors).length; i++){
          if($('input[name="radio'+i+'"]:checked').val() === undefined ){
            return false;
          }
        }
        return true;
    }

  onSelectionChange(entry) {
        console.log(entry);
    }
    

}
