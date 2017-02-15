import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule} from '@angular/router';

import { 
  EmployeeService
 } from './index';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [

  ],
  providers: [
    EmployeeService
  ]
})
export class EmployeeModule { }
