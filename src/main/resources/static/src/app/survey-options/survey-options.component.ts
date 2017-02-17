import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey-options',
  templateUrl: './survey-options.component.html',
  styleUrls: ['./survey-options.component.css', '../app.component.css']
})
export class SurveyOptionsComponent implements OnInit {
  currentUrl: string
  selectSurvey: boolean
  newSurveyForm: FormGroup
  evaluator: FormControl
  evaluated: FormControl
  relationship: FormControl
  competenceToEvaluate: FormControl

  // We are creating a new object and setting its type to FormGroup
  complexForm : FormGroup;

  // We are passing an instance of  Router to our constructor
  // We are passing an instance of the FormBuilder to our constructor
  constructor(private router:Router, formBuilder: FormBuilder) { }

  ngOnInit() { 
    // Popover    
    (<any> $('[data-toggle="popover"]')).popover({
        html: true,
        content: function () {
            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
            return clone;
        }
      });
      // Function used if the evaluator is the Client, we change the SELECT for an INPUT TEXT
      // With that, the client will be able to put his name.
      $("#relationshipSelect").focusout(function(){
          if ($("#relationshipSelect option:selected").attr('id') === "client"){
            $("#evaluatorAppEmployee").addClass('hide');  
            $("#evaluatorAppEmployeeText").removeClass('hide');   
            $("label[for='evaluatorSelect']").removeClass('hide');                       
          }else if($("#relationshipSelect option:selected").attr('id') === "own"){
            $("#evaluatorAppEmployee").addClass('hide'); 
            $("#evaluatorAppEmployeeText").addClass('hide');  
            $("label[for='evaluatorSelect']").addClass('hide');
          }else{
            $("#evaluatorAppEmployee").removeClass('hide'); 
            $("label[for='evaluatorSelect']").removeClass('hide'); 
            $("#evaluatorAppEmployeeText").addClass('hide'); 
          }
      });
      
      this.currentUrl = this.router.url
      this.selectSurvey = this.isOnePath();
      
      this.evaluated = new FormControl('')
      this.evaluator = new FormControl('')
      this.relationship = new FormControl('')
      this.competenceToEvaluate = new FormControl('')

      this.newSurveyForm = new FormGroup({
        evaluated: this.evaluated,
        evaluator: this.evaluator,
        relationship: this.relationship,
        competenceToEvaluate: this.competenceToEvaluate
      })
  }

  isOnePath() : boolean {
    return this.currentUrl == "/survey-setup" ? true : false 
  }
}
