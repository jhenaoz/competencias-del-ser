import { Component } from '@angular/core';
import { TranslateService } from 'ng2-translate/src/translate.service';
import { ISurvey } from './survey/survey-model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  survey: ISurvey

  constructor(private translate: TranslateService) {
    //Language setup
    translate.addLangs(['en', 'es']);
    translate.setDefaultLang('es');
    translate.use('es');
  }

  startSurvey(survey: ISurvey){
    this.survey.evaluator = survey.evaluator
    this.survey.role = survey.role
    this.survey.evaluated.push(survey.evaluated[0])
  }

}
