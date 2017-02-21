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


}
