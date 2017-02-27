import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';

import { FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { SurveyService } from './survey.service';
import { Survey } from './survey.model';

import {
 Aptitude,
 AptitudeService,
 Behavior
} from '../aptitude/index';

import 'rxjs/add/operator/toPromise';

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

  aptitude: Aptitude;

  behaviors: Behavior[];
  behaviorLenght = 0;

  observation: string;

  id: string;

  observableLegth = 0;

  surveyForm: FormGroup;
  survey: Survey = new Survey();

  constructor(private surveyService: SurveyService,
      private _aptitudeService: AptitudeService,
      private translate: TranslateService,
      private route: ActivatedRoute,
      private router:  Router,
      private fb: FormBuilder) {
        this.currentLanguage = translate.currentLang;
  }

  ngOnInit() {
    this.aptitude = new Aptitude();
    this.route.params.subscribe(param => {
          this.id = param['id'];
          this._aptitudeService.getBehaviors(this.id)
            // .subscribe(behaviors => this.behaviors = behaviors, error => this.errorMessage = <any>error);
             .toPromise()
            .then(behaviors => {
                this.behaviors = behaviors,  error => this.errorMessage = <any>error;
                console.log('this.behaviors',  this.behaviors.length);
             });
     });

    this.surveyForm = this.fb.group({
      answers: this.fb.array([this.buildAnswer()]),
      observation: '',
    });

    for (let i = 1; i < 5; i++) {
      this.answers.push(this.buildAnswer());
    }
  }

  buildAnswer(): FormGroup {
    return this.fb.group({
      option: ''
    });
  }
  click() {
    for (let i = 0; i < 5; i++) {
       $('.validateRadio' + i + '').click(function() {
        $('.validateRadio' + i + '').not(this).prop('checked', false);
      });
    }
  }
   get answers(): FormArray{
        return <FormArray>this.surveyForm.get('answers');
    }

    addAnswers(): void {
      for (let i = 1; i < 5; i++) {
        this.answers.push(this.buildAnswer());
      }
    }

    save(model: any, isValid: boolean) {

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
    this.aptitude.aptitudeId = this.id;
    this.aptitude.observation = this.surveyForm.controls['observation'].value;
    // this will be filled with the radio buttons selections from the user
    this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = 1;
    this.aptitude.behaviors = this.behaviors;
    this.surveyService.survey.aptitudes.push(this.aptitude);
    // console.log(this.surveyService.survey);
    if (!this.surveyService.oneSurvey) {
      this.router.navigate(['404']);
    }else {
      if (+this.id + 1 >= 9) {
        this.router.navigate(['404']);
      }else {
        const next = (+this.id + 1).toString();
        this.router.navigate(['survey/' + next]);
      }
      const next = (+this.id + 1).toString();
      this.router.navigate(['survey/' + next]);
      $('.active').next().addClass('active');
    }
  }




}
