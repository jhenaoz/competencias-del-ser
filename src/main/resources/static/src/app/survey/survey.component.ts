import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { SurveyService } from './survey.service';

import {
 Aptitude,
 AptitudeService,
 Behavior
} from '../aptitude/index';

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

  id: string;

  constructor(private surveyService: SurveyService,
      private _aptitudeService: AptitudeService,
      private translate: TranslateService,
      private route: ActivatedRoute,
      private router:  Router) {
        this.currentLanguage = translate.currentLang;
        route.params.subscribe(param => {
          this.id = param['id'];
          this._aptitudeService.getBehaviors(this.id)
            .subscribe(behaviors => this.behaviors = behaviors, error => this.errorMessage = <any>error);
            // , () => this.asdf =  this.behaviors.length
          // this._aptitudeService.getBehaviorLength(this.id).subscribe(asdf => this.asdf = asdf, error => this.errorMessage = <any>error);

     });

  }

  ngOnInit() {
    this.next = 'logros';
  }

/*
* Hardcoded values
*/
  nextAptitude() {
    if  (this.next !== '') {
       $('#openess').addClass('active');
    }else {
      $('.active').next().addClass('active');
    }
  }


  save(model: any, isValid: boolean) {
       if  (this.validateSurvey()) {
        $('#radio-alert').addClass('hide');
        $('input:checked').removeAttr('checked');
       }else {
         $('#radio-alert').removeClass('hide');
       }
        for  (let i = 1; i <= Object.keys(this.behaviors).length; i++) {
          alert($('input[name="radio' + i + '"]:checked').val()); ;
        }
    }

    validateSurvey(): boolean {
      for  (let i = 1; i <= Object.keys(this.behaviors).length; i++) {
          if  ($('input[name="radio' + i + '"]:checked').val() === undefined ) {
            return false;
          }
        }
        return true;
    }

  saveSurvey(survey) {
    if (!survey) { return; }
    this.surveyService.saveSurvey(survey)
                     .subscribe(
                       res  => this.surveyService.survey = res,
                       error =>  this.errorMessage = <any>error);
  }


  onSelectionChange(entry) {
        console.log(entry);
    }

  surveyAdvance() {
    if (!this.surveyService.oneSurvey) {
      this.router.navigate(['404']);
    }else {
        if (+this.id + 1 >= 9) {
        this.router.navigate(['404']);
      }else {
        const next = (+this.id + 1).toString();
        this.router.navigate(['survey/' + next]);
      }
    }
  }




}
