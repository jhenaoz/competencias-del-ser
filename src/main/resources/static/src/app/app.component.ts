import { Component } from '@angular/core';
import { TranslateService } from 'ng2-translate/src/translate.service';
import { Survey } from './survey/survey-model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  survey: Survey

  constructor(private translate: TranslateService) {
    //Language setup
    translate.addLangs(['en', 'es']);
    translate.setDefaultLang('es');
    translate.use('es');
  }

  startSurvey(survey: Survey){
    this.survey.evaluator = survey.evaluator
    this.survey.role = survey.role
    this.survey.evaluated.push(survey.evaluated[0])
    this.testPrint()
  }

  testPrint(){
    console.log(this.survey.evaluator)
    console.log(this.survey.role)
    console.log(this.survey.evaluated)

  }

}
