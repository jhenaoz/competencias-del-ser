import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule} from '@angular/router';

import {
  EmployeeService,
  EmployeeComponent
 } from './index';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    EmployeeComponent
  ],
  providers: [
    EmployeeService
  ]
})
export class EmployeeModule { }
