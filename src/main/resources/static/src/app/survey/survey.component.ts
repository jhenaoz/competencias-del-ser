import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';

import { FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { SurveyService } from './survey.service';

import { LocalStorageService } from 'angular-2-local-storage';

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
  /*
  * Route variables
  */
  public next: string;
  errorMessage: string;

  /*
  * i18n variables
  */
  currentLanguage: string;

  /*
  * Survey variables
  */
  aptitude: Aptitude;
  behaviors: Behavior[];
  /*
  * Survey form components and variables
  */
  id: string;
  observation: string;
  surveyForm: FormGroup;
  survey: Survey;
  showForm: boolean;

  /**
   * Creates an instance of SurveyComponent.
   * @param {SurveyService} surveyService
   * @param {AptitudeService} _aptitudeService
   * @param {TranslateService} translate
   * @param {ActivatedRoute} route
   * @param {Router} router
   * @param {FormBuilder} fb
   * 
   */
  constructor(private surveyService: SurveyService,
    private _aptitudeService: AptitudeService,
    private translate: TranslateService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private localStorageService: LocalStorageService) {
    this.survey = this.surveyService.survey;
    console.log(this.survey)
    this.currentLanguage = translate.currentLang;
    this.createForm();
  }

  /*
  * Async method to initializate survey variables
  */
  async ngOnInit() {
    // Aptitude instance
    this.aptitude = new Aptitude();
    // We wait to get the route id param
    await this.route.params.subscribe(param => {
      this.id = param['id'];
    });
    // We wait to get the behaviors from aptitudeService
    const behaviors: Behavior[] = await this._aptitudeService.getBehaviors(this.id).toPromise();
    this.behaviors = behaviors;
    // We initializate the form pushing new form-groups to the root group
    for (let i = 1; i < behaviors.length; i++) {
      this.behaviorSurvey.push(this.buildBehaviorSurvey(behaviors[i].id));
    }
    // Show the form
    this.showForm = true;

    // Test for localstorage
    localStorage.setItem('test', 'probandooo');
  }

  /*
  * Method to create the survey form
  */
  createForm() {
    this.surveyForm = this.fb.group({
      // We manually add the first element
      behaviorSurvey: this.fb.array([this.buildBehaviorSurvey('1')]),
      observation: '',
    });
  }

  /*
  * Method to create form group for behaviors array
  */
  buildBehaviorSurvey(id): FormGroup {
    return this.fb.group({
      behaviorId: id,
      score: ''
    });
  }

  /*
  * Method to bind radio buttons
  * XXX: Search how to do that with angular o typescript
  */
  bindRadioButtons() {
    for (let i = 0; i < 5; i++) {
      $('.validateRadio' + i + '').click(function () {
        $('.validateRadio' + i + '').not(this).prop('checked', false);
      });
    }
  }

  /*
  * Method to get behaviors form array
  */
  get behaviorSurvey(): FormArray {
    return <FormArray>this.surveyForm.get('behaviorSurvey');
  }

  /*
  * Method to advance in the survey 
  * XXX: Search a better way to handle routing
  */
  surveyAdvance() {
    if (this.surveyForm.valid) {
      // Filling aptitud properties 
      this.aptitude.aptitudeId = this.id;
      this.aptitude.observation = this.surveyForm.controls['observation'].value;
      this.aptitude.behaviors = this.surveyForm.controls['behaviorSurvey'].value;
      // Pushing aptitud into survey
      this.survey.aptitudes.push(this.aptitude)
      // Checks if is only one survey (one competence to evaluate)
      if (!this.surveyService.oneSurvey) {
        this.router.navigate(['404']);
        // Saves survey
        this.surveyService.saveSurvey(this.survey)
      } else {
        // Change route to advance
        // XXX: Kind of hardcoding to me... (?)
        if (+this.id + 1 >= 9) {
          this.router.navigate(['404']);
          // Saves survey
          this.surveyService.saveSurvey(this.survey)
        } else {
          const next = (+this.id + 1).toString();
          this.router.navigate(['survey/' + next]);
          // Change CSS class to change color to the aptitudes buttons
          $('.active').next().addClass('active');
          // Hide survey form until data is ok
          this.showForm = false;
          // Set time to wait functions to complete
          setTimeout(() => {
            // Clear form
            this.createForm();
            // Init all variables again
            this.ngOnInit();
          });
        }
      }

    }

  }

}
