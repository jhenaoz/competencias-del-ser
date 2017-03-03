import { Component, OnInit } from '@angular/core';
import { SurveyService } from '../survey/survey.service';
import { LocalStorageService } from 'angular-2-local-storage';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css', '../app.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private surveyService: SurveyService,
              private localStorageService: LocalStorageService) { }

  ngOnInit() {
  }

  OneData(value) {
    this.surveyService.oneSurvey = value;
    localStorage.setItem('typeOfSurvey', value);
  }

}
