import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SurveyService } from '../survey/survey.service';
import { Survey } from '../survey/survey.model';

@Component({
  selector: 'app-survey-end',
  templateUrl: './survey-end.component.html',
  styleUrls: ['./survey-end.component.css', '../app.component.css']
})
export class SurveyEndComponent implements OnInit {

  constructor(private surveyService: SurveyService,
              private router: Router) { }

  ngOnInit() {
  }

  evaluateMore() {
    this.surveyService.survey = new Survey();
    this.router.navigate(['/surveyteam-setup']);
  }

  finish() {
    this.serviceClean();
    this.router.navigate(['/welcome']);
  }

  serviceClean() {
    this.surveyService.survey = new Survey();
    this.surveyService.oneSurvey = null;
    this.surveyService.competence = '';
    this.surveyService.evaluator = '';
    this.surveyService.role = '';
  }


}
