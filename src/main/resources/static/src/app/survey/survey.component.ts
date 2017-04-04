import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';

import { FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { SurveyService } from './survey.service';

import { LocalStorageService } from 'angular-2-local-storage';

import { Survey } from './survey.model';

import { SurveyRouteActivator } from './survey.route.activator.service';

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
  showForm: boolean;
  submitted: boolean;
  textAreaIsRequired: boolean;

  /**
   * Creates an instance of SurveyComponent.
   * @param {SurveyService} surveyService
   * @param {AptitudeService} _aptitudeService
   * @param {TranslateService} translate
   * @param {ActivatedRoute} route
   * @param {Router} router
   * @param {FormBuilder} fb
   * @param {LocalStorageService} localStorage
   */
  constructor(private surveyService: SurveyService,
    private _aptitudeService: AptitudeService,
    private translate: TranslateService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private localStorageService: LocalStorageService,
    private guard: SurveyRouteActivator) {
    this.currentLanguage = translate.currentLang;
    this.createForm();
  }

  /*
  * Async method to initializate survey variables
  */
  async ngOnInit() {
    this.submitted = false;
    this.textAreaIsRequired = false;
    // We wait to get the route id param
    await this.route.params.subscribe(param => {
      this.id = param['id'];
    });
    // Add visual effect on buttons
    this.paintButtons(this.id);

    if (!this.surveyService.oneSurvey) {
      this.id = this.surveyService.competenceId;
    }
    // Aptitude instance
    this.aptitude = new Aptitude();
    // Verify if there is a survey stored in localstorage to bring it and continue the survey
    this.verifyStoredSurvey();
    // We wait to get the behaviors from aptitudeService
    const behaviors: Behavior[] = await this._aptitudeService.getBehaviors(this.id).toPromise();
    this.behaviors = behaviors;
    // We initializate the form pushing new form-groups to the root group
    for (let i = 1; i < behaviors.length; i++) {
      this.behaviorSurvey.push(this.buildBehaviorSurvey(behaviors[i].id));
    }
    // Show the form
    this.showForm = true;
    if (+this.id > this.surveyService.survey.aptitudes.length) {
      this.surveyService.goingBack = false;
    }
    if (this.surveyService.goingBack) {
      // If we are coming back, so lets fill the form with the values completed by the user.
      $(document).ready(() => {
        this.refillSurvey(this.surveyService.survey.aptitudes[+this.id - 1]);
      });
    }
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
      $('.validateRadio' + i).click(function () {
        $('.validateRadio' + i).not(this).prop('checked', false);
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
    this.submitted = true;
      if (this.surveyForm.valid) {
      // Filling behaviors
      this.aptitude.behaviors = this.surveyForm.controls['behaviorSurvey'].value;
      // Check if some selected score is under 2
      for (let i = 0; i < Object.keys(this.aptitude.behaviors).length; i++) {
        if ((this.aptitude.behaviors[i].score <= 2)) {
          this.textAreaIsRequired = true;
        }
      }
      // Check if textarea is filled
      this.observation = this.surveyForm.get('observation').value.trim();
      if ((this.observation.length < 10 || this.hasWhiteSpace(this.observation)) && this.textAreaIsRequired) {
        return;
      }
      // Filling aptitud properties
      this.aptitude.aptitudeId = this.id;
      this.aptitude.observation = this.surveyForm.controls['observation'].value.trim();
      // Pushing aptitud into survey
      if (this.surveyService.goingBack) {
        this.surveyService.survey.aptitudes[+this.id - 1] = this.aptitude;
      } else {
        this.surveyService.survey.aptitudes.push(this.aptitude);
      }
      // Stored actual survey to localstorage
      localStorage.setItem('storedSurvey', JSON.stringify(this.surveyService.survey));
      // Checks if is only one survey (one competence to evaluate)
      if (!this.surveyService.oneSurvey) {
        // Redirect to final
        this.router.navigate(['final']);
        // Saves survey
        localStorage.clear();
        this.surveyService.saveSurvey(this.surveyService.survey);
      } else {
        // Change route to advance
        // XXX: Kind of hardcoding to me... (?)
        if (+this.id + 1 >= 9) {
          // Redirect to final
          this.router.navigate(['final']);
          localStorage.clear();
          // Saves survey
          this.surveyService.saveSurvey(this.surveyService.survey);
        } else {
          const next = (+this.id + 1).toString();
          this.guard.allow = true;
          this.router.navigate(['survey/' + next]);
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

  /*
  * Method to look for stored survey on localstorage
  */
  verifyStoredSurvey() {
    const storedSurvey = <Survey>JSON.parse(localStorage.getItem('storedSurvey'));
    this.surveyService.competence = localStorage.getItem('competence');
    // If there is a stored survey
    if (storedSurvey) {
      // Call method to verify if the stored survey is the same the user wants to complete
      if (this.verifySameSurvey(storedSurvey)) {
        // Save into the service the stored survey
        this.surveyService.survey = storedSurvey;
        const evaluatedAptitudes = storedSurvey.aptitudes.length;
        if (+this.id < evaluatedAptitudes) {
          this.surveyService.goingBack = true;
        }
      }
    }
  }

  paintButtons(value) {
    for (let i = 1; i < value; i++) {
      $('#' + i).next().addClass('active');
    }
  }

  /*
   * Method to verify if the survey stored in LocalStorage is
   * the same type of Survey and has the same evaluated and evaluator
   */
  verifySameSurvey(value: Survey) {
    const typeOfSurvey = (localStorage.getItem('typeOfSurvey') === 'true');
    return this.surveyService.survey.evaluated === value.evaluated &&
              this.surveyService.survey.evaluator === value.evaluator
              && this.surveyService.oneSurvey ===  typeOfSurvey;
  }

  /*
   * Method to verify the amount of whitespaces contained in the string
   */
  hasWhiteSpace(s) {
    return s.indexOf('   ') >= 1;
  }

  /*
   * Method to fill the form with the values that were given by the user
   */
  refillSurvey(value) {
    // Fill the observations
    this.surveyForm.get('observation').setValue(value.observation);
    // Fill each radio button with the score
    value.behaviors.forEach(behavior => {
      // it has the '4 -' because the radio buttons from left to side but the max score '4'
      // is the one from the left
      const score = 4 - behavior.score;
      $('#' + behavior.behaviorId + 'radio' + score).trigger('click');
    });
  }

  /*
   * Method to go back to the previous aptitude
   */
  goBack() {
    // If we have not completed any aptitude, then we have not an aptitud to go back
    if (!this.surveyService.oneSurvey || this.id === '1') {
      this.router.navigate(['welcome']);
    } else {
      this.surveyService.goingBack = true;
      const previous = (+this.id - 1).toString();
      // remove the blue color on the actual button
      $('#' + this.id).removeClass('active');
      this.guard.allow = true;
      this.router.navigate(['survey/' + previous]).then(() => {
         // Hide survey form until data is ok
        this.showForm = false;
        // Clear form
        this.createForm();
        // Init all variables again
        this.ngOnInit();
      });
    }
  }

}
