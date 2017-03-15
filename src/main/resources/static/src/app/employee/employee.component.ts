import { Component, OnInit } from '@angular/core';

import {
  IEmployee,
  EmployeeService
} from './index';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['../survey-options/survey-options.component.css']
})
export class EmployeeComponent implements OnInit {
  errorMessage: string;
  employees: IEmployee[];

  constructor(private _employeeService: EmployeeService) { }

  ngOnInit() {
    // Get all employees from service
    this._employeeService.getEmployees()
      .subscribe(employees => this.employees = employees, error => this.errorMessage = <any>error);
  }

}
