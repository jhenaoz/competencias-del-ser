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

      $("select[title='relationshipSelect']").focusout(function(){
          // Now see if it's the value you want
          if ($(this).val() === "Cliente"|| $(this).val() === "Client"){
            $("#evaluatorAppEmployee").addClass('hide');  
            $("#evaluatorAppEmployeeText").removeClass('hide');            
          }else{
            $("#evaluatorAppEmployee").removeClass('hide');  
            $("#evaluatorAppEmployeeText").addClass('hide'); 
          }
      });

  }

}
