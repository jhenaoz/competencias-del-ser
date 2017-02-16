import { Component, OnInit } from '@angular/core';

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey-options',
  templateUrl: './survey-options.component.html',
  styleUrls: ['./survey-options.component.css', '../app.component.css']
})
export class SurveyOptionsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    (<any> $('[data-toggle="popover"]')).popover({
        content: $('#pop-content').addClass('.popover').html(),
        html: true
      });
  }

}
