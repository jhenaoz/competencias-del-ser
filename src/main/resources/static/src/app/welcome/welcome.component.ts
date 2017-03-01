import { Component, OnInit } from '@angular/core';
import { SurveyService } from '../survey/survey.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css', '../app.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private surveyService: SurveyService) { }

  ngOnInit() {
  }

  OneData(value) {
    this.surveyService.oneSurvey = value;
  }

}
