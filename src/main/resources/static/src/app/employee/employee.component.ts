import { Component, OnInit } from '@angular/core';

import { 
  IEmployee,
  EmployeeService 
} from './index';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {
  pageTitle: string = 'Product List';

  errorMessage: string;
  employees: IEmployee[]

  constructor(private _employeeService: EmployeeService) { }

  ngOnInit() {
    this._employeeService.getEmployees().subscribe(employees => this.employees = employees, error => this.errorMessage = <any>error)
    /*this.employees.sort(function(a, b){
    return a.name == b.name ? 0 : +(a.name > b.name) || -1;

    });*/
  }

}
