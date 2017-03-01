
import { Component, OnInit, OnDestroy, EventEmitter, Input, Output, OnChanges } from '@angular/core';

import { Router } from '@angular/router';
import { FormControl, FormGroup, FormBuilder, Validators, AbstractControl, ValidatorFn } from '@angular/forms';

import { Survey } from '../survey/survey.model';
import { Aptitude, Behavior } from '../aptitude/index';
import { IEmployee } from '../employee/employee.model';
import { SurveyService } from '../survey/survey.service';

import { TranslateService } from 'ng2-translate/src/translate.service';

import * as jQuery from 'jquery';

/*
* Custom evaluator validator
*/
function evaluatorValidator(self: boolean): ValidatorFn {
  return (c: AbstractControl): { [key: string]: boolean } | null => {
    if (self) {
      return { 'valid': true };
    }
    return null;
  };
}
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
  evaluator: FormControl;
  evaluated: FormControl;
  role: FormControl;
  competenceToEvaluate: FormControl;
  // We are creating a new object and setting its type to FormGroup
  complexForm: FormGroup;

  survey: Survey = new Survey();

  /*
  * Relation variables
  */
  // Boolean to know if is self assessment
  isSelf: boolean = false;
  isClient: boolean = false;
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
  client_orientationText: string;
  goalsText: string;
  teamworkText: string;
  developmentText: string;
  goalText: string;

  // We are passing an instance of  Router to our constructor
  // We are passing an instance of the FormBuilder to our constructor
  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private surveyService: SurveyService,
    private translate: TranslateService) { }

  ngOnInit() {
    /*
    * Popover function
    */
    (<any>$('[data-toggle="popover"]')).popover({
      html: true,
      content: function () {
        const clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
        return clone;
      }
    });
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
      this.client_orientationText = res.competence_client_orientation;
      this.goalsText = res.competence_goals;
      this.teamworkText = res.competence_teamwork;
      this.developmentText = res.competence_development;
      this.goalText = res.competence_goal;
    });
    /*
    * Form variables initialization
    * FormControl('value', validator)
    */
    this.isSelf = false;
    this.evaluated = new FormControl('', Validators.required);
    this.evaluator = new FormControl('', evaluatorValidator(this.isSelf));
    this.role = new FormControl('', Validators.required);
    this.competenceToEvaluate = new FormControl('', Validators.required);
    // We choose the constructor depending of the type of survey
    if (!this.surveyService.oneSurvey) {
      this.complexForm = this.formBuilder.group({
        evaluated: this.evaluated,
        evaluator: this.evaluator,
        role: this.role,
        competenceToEvaluate: this.competenceToEvaluate
      });
    } else {
      this.complexForm = this.formBuilder.group({
        evaluated: this.evaluated,
        evaluator: this.evaluator,
        role: this.role,
      });
    }
  }

  divisibleByTen(control: AbstractControl) {
    return parseInt(control.value) % 10 == 0 ? null : {
      divisibleByTen: true
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
  * Function to get from value
  */
  submitForm() {
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
    switch (value.competenceToEvaluate) {
      case this.openessText:
        value.competenceToEvaluate = 'openess';
        break;
      case this.communicationText:
        value.competenceToEvaluate = 'communication';
        break;
      case this.initiativeText:
        value.competenceToEvaluate = 'initiative';
        break;
      case this.client_orientationText:
        value.competenceToEvaluate = 'client_orientation';
        break;
      case this.goalsText:
        value.competenceToEvaluate = 'achivement_and_results';
        break;
      case this.teamworkText:
        value.competenceToEvaluate = 'teamwork';
        break;
      case this.developmentText:
        value.competenceToEvaluate = 'development_oriented_leadership';
        break;
      case this.goalText:
        value.competenceToEvaluate = 'achivement_oriented_leadership';
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
    this.surveyService.startSurvey(this.survey);
    // XXX: hardcoded
    this.router.navigate(['/survey/1']);

  }

  /*
  * Function to store the form values into the survey variable in the service
  */
  startSurvey() {
    // Next steps are just for testing component's communication, they are not yet the real way.
    if (this.complexForm.valid) {
      this.submitForm();
    }
  }



}
