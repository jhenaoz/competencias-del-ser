
import { Component, OnInit, EventEmitter, Output, OnChanges } from '@angular/core';

import { Router } from '@angular/router';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';

import { ISurvey, IAptitude, IBehavior } from '../survey/survey-model';
import { IEmployee } from '../employee/employee.model';
import { SurveyService } from '../survey/survey.service';

import * as jQuery from 'jquery';

@Component({
  selector: 'app-survey-options',
  templateUrl: './survey-options.component.html',
  styleUrls: ['./survey-options.component.css', '../app.component.css']
})
export class SurveyOptionsComponent implements OnInit, OnChanges {
  currentUrl: string;
  selectSurvey: boolean;
  @Output() startSurveyAll= new EventEmitter

  public relationshipType: String;
  // newSurveyForm: FormGroup
  evaluator: FormControl
  evaluated: FormControl
  relationship: FormControl
  competenceToEvaluate: FormControl

  // We are creating a new object and setting its type to FormGroup
  complexForm : FormGroup;

  // We are passing an instance of  Router to our constructor
  // We are passing an instance of the FormBuilder to our constructor
  constructor(private router:Router, private formBuilder: FormBuilder, private surveyService: SurveyService) { 
  }
  
  ownValidator(){
     const validator = {
      'evaluated': [null, Validators.required],
      'relationship': [null, Validators.required],
      'competenceToEvaluate': [null, Validators.required]
    }
    if(this.relationshipType === "own"){
      let validator = {
        'evaluated': [null, Validators.required],
        'relationship': [null, Validators.required],
        'competenceToEvaluate': [null, Validators.required]
      }
    }
   return validator;
  }

  ngOnInit() { 

    this.currentUrl = this.router.url
    this.selectSurvey = this.isOnePath();
  
    (<any> $('[data-toggle="popover"]')).popover({
        html: true,
        content: function () {
            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
            return clone;
        }
      });
      this.evaluated = new FormControl('',  Validators.required);
      this.evaluator = new FormControl('');
      this.relationship = new FormControl('',  Validators.required);
      this.competenceToEvaluate = new FormControl('',  Validators.required);

      if(!this.selectSurvey){
        this.complexForm = this.formBuilder.group({
          evaluated: this.evaluated,
          evaluator: this.evaluator,
          relationship: this.relationship,
          competenceToEvaluate: this.competenceToEvaluate
        })
      }else{
         this.complexForm = this.formBuilder.group({
          evaluated: this.evaluated,
          evaluator: this.evaluator,
          relationship: this.relationship,
        })
      }

      // Function used if the evaluator is the Client, we change the SELECT for an INPUT TEXT
      // With that, the client will be able to put his name.
      $("#relationshipSelect").focusout(function(){
          this.relationshipType = $("#relationshipSelect option:selected").attr('id');
          if ( this.relationshipType === "client" ){
            $("#evaluatorAppEmployee").addClass('hide');  
            $("#evaluatorAppEmployeeText").removeClass('hide');   
            $("label[for='evaluatorSelect']").removeClass('hide');                       
          }else if( this.relationshipType === "own" ){
           // $("#evaluatorAppEmployee").addClass('hide'); 
            $('#evaluatorAppEmployee').find('option[value="' + $("#evaluatedAppEmployee option:selected").text() + '"]').prop('selected', true);
            $("#evaluatorAppEmployeeText").addClass('hide');  
           // $("label[for='evaluatorSelect']").addClass('hide');
          }else{
            $("#evaluatorAppEmployee").removeClass('hide'); 
            $("label[for='evaluatorSelect']").removeClass('hide'); 
            $("#evaluatorAppEmployeeText").addClass('hide'); 
          }
      });

      $("#footerButton").click(function() {
        alert( "Handler for .click() called." );
      });
  }

  ngOnChanges(){
   
  }

  relationChanges(){

  }

  isOnePath() : boolean {
    return this.currentUrl == "/survey-setup" ? true : false 
  }

 /* submitForm(value: any){
    this.evaluator = new FormControl('', Validators.required);

    this.complexForm = this.formBuilder.group({
      evaluated: this.evaluated,
      evaluator: this.evaluator,
      relationship: this.relationship,
    })
    console.log(value);
  }*/

  startSurvey(){
    
  
    //Next steps are just for testing component's communication, they are not yet the real way.
/*    let survey: ISurvey = {
        evaluator: "evaluator1",
        evaluated: [{employeeId: 1, name: "evaluated1"}],
        role: "teammate",
        aptitudes: <IAptitude[]>[]
    }
    this.surveyService.startSurvey(survey)*/
    this.router.navigate(['/survey'])
    
    // this.startSurveyAll.emit(survey)

  }


}
