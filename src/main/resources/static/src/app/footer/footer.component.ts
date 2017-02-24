import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/pairwise';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css', '../app.component.css']
})
export class FooterComponent implements OnInit {
  @Output() startNewSurvey = new EventEmitter
  currentUrl: string
  constructor(private router:Router) { }

  ngOnInit() {
    this.currentUrl = this.router.url
  }

/*
* Method to go back given the current url
*/
  cancel(){
    switch (this.currentUrl) {
      case '/survey-setup': this.router.navigate(['welcome']); break;
      case '/surveyteam-setup': this.router.navigate(['welcome']); break;
      case '/survey/1': this.router.navigateByUrl('survey/2'); break;
      default:
        break;
    }
  }

/*
* Method to go forward given the current url
*/
  advance(){
    switch (this.currentUrl) {
      case '/survey-setup': this.startNewSurvey.emit()
        break; 
      case '/surveyteam-setup':this.startNewSurvey.emit()
        break;
      case '/survey/1': this.router.navigateByUrl('survey/2'); break;
      default: 
        break;
    }
  }

}
