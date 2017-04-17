import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/pairwise';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css', '../app.component.css']
})
export class FooterComponent implements OnInit {
  @Output() startNewSurvey = new EventEmitter;
  @Output() surveyAdvance = new EventEmitter;
  @Output() goBack = new EventEmitter;
  currentUrl;
  constructor(private router: Router) { }

  ngOnInit() {
    this.currentUrl = this.router.url;
  }

/*
* Method to go back given the current url
*/
  cancel() {
    switch (this.currentUrl) {
      case '/survey-setup': case '/surveyteam-setup':
        this.router.navigate(['welcome']); break;
      case '/survey/1':
        this.goBack.emit(); break;
      default:
        break;
    }
  }

/*
* Method to go forward given the current url
*/
  advance() {
    switch (this.currentUrl) {
      case '/survey-setup': this.startNewSurvey.emit();
        break;
      case '/surveyteam-setup': this.startNewSurvey.emit();
        break;
      case '/survey/1':
        this.surveyAdvance.emit(); break;
      default:
        break;
    }
  }

  changeButtonsText() {
    return this.currentUrl === '/survey-setup' || this.currentUrl === '/surveyteam-setup';
  }

}
