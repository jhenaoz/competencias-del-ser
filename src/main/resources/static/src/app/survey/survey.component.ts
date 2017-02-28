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
  behaviorLenght =  {};

  observation: string;

  id: string;

  observableLegth: number[] = [];

  surveyForm: FormGroup;
  survey: Survey;
  
  showForm: boolean = true;

  constructor(private surveyService: SurveyService,
      private _aptitudeService: AptitudeService,
      private translate: TranslateService,
      private route: ActivatedRoute,
      private router:  Router,
      private fb: FormBuilder) {
        this.survey = this.surveyService.survey;
        console.log(this.survey)
        this.currentLanguage = translate.currentLang;
        this.createForm();
  }

  async ngOnInit() {
      this.aptitude = new Aptitude();
      await this.route.params.subscribe(param => {
        this.id = param['id'];
      });
      const behaviors: Behavior[] = await this._aptitudeService.getBehaviors(this.id).toPromise();
      this.behaviors = behaviors;
      this.observableLegth.push(behaviors.length)
      for(let i = 1; i < behaviors.length; i++){
        this.behaviorSurvey.push(this.buildBehaviorSurvey(behaviors[i].id));
      } 
      this.showForm = true;
      
  }

  createForm(){
      this.surveyForm = this.fb.group({
          behaviorSurvey: this.fb.array([this.buildBehaviorSurvey('1')]),
          observation: '',
        });
  }

  buildBehaviorSurvey(id): FormGroup {
    return this.fb.group({
      behaviorId: id,
      score: ''
    });
  }

  bindRadioButtons() {
    for (let i = 0; i < 5; i++) {
       $('.validateRadio' + i + '').click(function() {
        $('.validateRadio' + i + '').not(this).prop('checked', false);
      });
    }
  }
   get behaviorSurvey(): FormArray{
        return <FormArray>this.surveyForm.get('behaviorSurvey');
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
    
    // this.aptitude.aptitudeId = this.id;
    // this.aptitude.observation = this.surveyForm.controls['observation'].value;
    // // this will be filled with the radio buttons selections from the user
    // this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = this.behaviors[0].score = 1;
    // this.aptitude.behaviors = this.behaviors;
    // this.surveyService.survey.aptitudes.push(this.aptitude);
    // // console.log(this.surveyService.survey);
    // if (!this.surveyService.oneSurvey) {
    //   this.router.navigate(['404']);
    // }else {
    //   if (+this.id + 1 >= 9) {
    //     this.router.navigate(['404']);
    //   }else {
    //     const next = (+this.id + 1).toString();
    //     this.router.navigate(['survey/' + next]);
    //   }
    //   const next = (+this.id + 1).toString();
    //   this.router.navigate(['survey/' + next]);
    //   $('.active').next().addClass('active');
    // }
      if(this.surveyForm.valid){
        this.aptitude.aptitudeId = this.id;
        this.aptitude.observation = this.surveyForm.controls['observation'].value;
        this.aptitude.behaviors = this.surveyForm.controls['behaviorSurvey'].value
        console.log(this.aptitude)
        this.survey.aptitudes.push(this.aptitude)
        console.log(this.survey)
        if (!this.surveyService.oneSurvey) {
          this.router.navigate(['404']);
        } else  {
          if (+this.id + 1 >= 9) {
            this.router.navigate(['404']);		       
          } else {		     
            const next = (+this.id + 1).toString();		       
            this.router.navigate(['survey/' + next]);		      
            $('.active').next().addClass('active');
            this.showForm = false;
            setTimeout(() => {
              this.createForm();
              this.ngOnInit();
            });
          }
        }

      }	

    }

}
