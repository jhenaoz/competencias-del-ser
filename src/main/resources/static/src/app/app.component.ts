import { Component } from '@angular/core';
import { TranslateService } from 'ng2-translate/src/translate.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private translate: TranslateService) {
    //Language setup
    translate.addLangs(['en', 'es']);
    translate.setDefaultLang('es');
    translate.use('es');
  }


}
