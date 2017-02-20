import { Component, OnInit } from '@angular/core';

import { TranslateService } from 'ng2-translate/src/translate.service';

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css', '../app.component.css']
})
export class SurveyComponent implements OnInit {

  public next: string;

  constructor() { }

  ngOnInit() {
    this.next = 'Orient';
  }

  nextAptitude(){
    // console.log(this.next);
    // $(".active").removeClass("active");
    // $(".col-md-1:contains('"+this.next+"')").addClass("active");

    // $('.row > .col-md-1').each(function() {
    //   console.log($(this).text());
    // });

    $(".active").next().addClass('active');
    
  }

}
