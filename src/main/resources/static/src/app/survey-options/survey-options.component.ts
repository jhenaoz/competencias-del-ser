
import { Component, OnInit, OnDestroy, EventEmitter, Input, Output, OnChanges } from '@angular/core';

import { Router } from '@angular/router';
import { FormControl, FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn } from '@angular/forms';

import { Survey } from '../survey/survey.model';
import { Aptitude, Behavior } from '../aptitude/index';
import { IEmployee } from '../employee/employee.model';
import { SurveyService } from '../survey/survey.service';

import { SurveyRouteActivator } from '../survey/survey.route.activator.service';

import { TranslateService } from 'ng2-translate/src/translate.service';

import { LocalStorageService } from 'angular-2-local-storage';

import { PopoverModule } from 'ng2-bootstrap';

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey-options',
  templateUrl: './survey-options.component.html',
  styleUrls: ['./survey-options.component.css', '../app.component.css']
})

export class SurveyOptionsComponent implements OnInit {

  public roleType: String;

  /*
  * Form variables
  */
  complexForm: FormGroup;
  survey: Survey = new Survey();
  submitted = false;
  evaluatorIsValid = true;
  teammateIsNotSelf = true;
  isRecent: Boolean = false;

  /*
  * Relation variables
  */
  // Boolean to know if is self assessment
  isSelf = false;
  isClient = false;
  // Variables to handle i18n relation translates --> self-assessment, client, teammate
  selfText: String;
  clientText: String;
  teammateText: String;

  /*
  * Competence variables
  */
  // Variables to handle i18n competences translates
  openessText: string;
  communicationText: string;
  initiativeText: string;
  clientOrientationText: string;
  goalsText: string;
  teamworkText: string;
  developmentText: string;
  goalText: string;

  // We are passing an instance of  Router to our constructor
  // We are passing an instance of the FormBuilder to our constructor
  constructor(private router: Router,
    private fb: FormBuilder,
    private surveyService: SurveyService,
    private translate: TranslateService,
    private localStorageService: LocalStorageService,
    private guard: SurveyRouteActivator) { }

  ngOnInit() {

    this.surveyService.oneSurvey = (localStorage.getItem('typeOfSurvey') === 'true');

    /*
    * Fetch variables from i18n .json files
    */
    // Relation variables initialization
    this.translate.get('Survey_options').subscribe(res => {
      this.selfText = res.relation_own;
      this.clientText = res.relation_client;
      this.teammateText = res.relation_teammate;
    });
    // Competence variables initialization
    this.translate.get('Competences').subscribe(res => {
      this.openessText = res.competence_opening;
      this.communicationText = res.competence_communication;
      this.initiativeText = res.competence_initiative;
      this.clientOrientationText = res.competence_client_orientation;
      this.goalsText = res.competence_goals;
      this.teamworkText = res.competence_teamwork;
      this.developmentText = res.competence_development;
      this.goalText = res.competence_goal;
    });
    // Form initialization
    this.createForm();
  }

  /*
  * Function to initializate formgroup variables
  */
  createForm() {
    if (!this.surveyService.oneSurvey) {
      this.complexForm = this.fb.group({
        evaluated: '',
        evaluator: '',
        role: '',
        competenceToEvaluate: ''
      });
    } else {
      this.complexForm = this.fb.group({
        evaluated: '',
        evaluator: '',
        role: '',
      });
    }
  }


  /*
  * Function to handle relation type changes
  */
  relationChange(value) {
    this.isSelf = this.selfText === value ? true : false;
    this.isClient = value === this.clientText ? true : false;
  }

  /*
  * Function to get form value
  */
  async submitForm() {
    const value = this.complexForm.value;
    // Object organization for data persistence
    switch (value.role) {
      case this.selfText:
        value.evaluator = value.evaluated;
        value.role = 'self-assessment';
        break;
      case this.clientText:
        value.role = 'client';
        break;
      case this.teammateText:
        value.role = 'teammate';
        break;
    }
    this.surveyService.competence = value.competenceToEvaluate;
    localStorage.setItem('competence', value.competenceToEvaluate);
    switch (value.competenceToEvaluate) {
      case this.openessText:
        value.competenceToEvaluate = 'openness';
        this.surveyService.competenceId = '1';
        break;
      case this.communicationText:
        value.competenceToEvaluate = 'communication';
        this.surveyService.competenceId = '2';
        break;
      case this.initiativeText:
        value.competenceToEvaluate = 'initiative';
        this.surveyService.competenceId = '3';
        break;
      case this.clientOrientationText:
        value.competenceToEvaluate = 'client_orientation';
        this.surveyService.competenceId = '4';
        break;
      case this.goalsText:
        value.competenceToEvaluate = 'achivement_and_results';
        this.surveyService.competenceId = '5';
        break;
      case this.teamworkText:
        value.competenceToEvaluate = 'teamwork';
        this.surveyService.competenceId = '6';
        break;
      case this.developmentText:
        value.competenceToEvaluate = 'development_oriented_leadership';
        this.surveyService.competenceId = '7';
        break;
      case this.goalText:
        value.competenceToEvaluate = 'achivement_oriented_leadership';
        this.surveyService.competenceId = '8';
        break;
      default:
        value.competenceToEvaluate = '';
        break;
    }
    // We fill all data into survey and pass it to the service
    this.survey.evaluator = value.evaluator;
    this.survey.evaluated = value.evaluated;
    this.survey.role = value.role;
    this.survey.aptitudes = new Array();
    this.isRecent = await this.surveyService
        .checkSurveyDate(this.survey, this.surveyService.competenceId, !this.surveyService.oneSurvey).toPromise();
    if (!this.isRecent) {
      this.gotoSurvey();
    } else {
      $('input, select').attr('disabled', 'disabled');
      this.complexForm.disable();
      this.complexForm.controls['evaluated'].disable();
    }
  }

  gotoSurvey() {
    this.surveyService.startSurvey(this.survey);
    // XXX: hardcoded
    this.guard.allow = true;
    this.router.navigate(['/survey/1']);
  }
  reset() {
    $('input, select').removeAttr('disabled');
    $('select').prop('selectedIndex', 0);
    this.complexForm.reset();
    this.complexForm.enable();
    this.submitted = false;
    this.isRecent = false;
    this.survey = new Survey();
  }

  /*
  * Function to store the form values into the survey variable in the service
  */
  startSurvey() {
    if ((this.complexForm.value.evaluator === '' ||
        this.complexForm.value.evaluator === null ||
        this.complexForm.value.evaluator === this.complexForm.value.evaluated) &&
        !this.isSelf) {
          if (this.complexForm.value.evaluator === this.complexForm.value.evaluated) {
            this.teammateIsNotSelf = false;
          } else {
            this.evaluatorIsValid = false;
          }
    } else {
      this.teammateIsNotSelf = true;
      this.evaluatorIsValid = true;
    }
    this.submitted = true;
    // Next steps are just for testing component's communication, they are not yet the real way.
    if (this.complexForm.valid && this.evaluatorIsValid && this.teammateIsNotSelf) {
      this.submitForm();
    }
  }
}
