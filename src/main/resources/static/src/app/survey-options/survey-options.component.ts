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
        html: true,
        content: function () {
            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
            return clone;
        }
      });
      // Function used if the evaluator is the Client, we change the SELECT for an INPUT TEXT
      // With that, the client will be able to put his name.
      // XXX - Organize if values
      $("select[title='relationshipSelect']").focusout(function(){
          if ($(this).val() === "Cliente"|| $(this).val() === "Client"){
            $("#evaluatorAppEmployee").addClass('hide');  
            $("#evaluatorAppEmployeeText").removeClass('hide');            
          }else if($(this).val() === "Auto-Valoración"){
            $("#evaluatorAppEmployee select").val($("#evaluatedAppEmployee option:selected" ).text());
            $("#evaluatorAppEmployee").prop("disabled", true);
            
            $("#evaluatorAppEmployee").removeClass('hide');  
            $("#evaluatorAppEmployeeText").addClass('hide'); 
          }else{
            $("#evaluatorAppEmployee").removeClass('hide');  
            $("#evaluatorAppEmployeeText").addClass('hide'); 
          }
      });

  }

}
