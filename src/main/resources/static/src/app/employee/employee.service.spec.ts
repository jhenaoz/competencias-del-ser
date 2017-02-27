/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { environment } from '../../environments/environment';

import {
  EmployeeService,
  EmployeeComponent,
  IEmployee
 } from './index';



describe('EmployeeService', () => {
  let employeeService: EmployeeService;
  let _employeeUrl = environment.apiURL += '/person';;
  let mockHttp;

  beforeEach(() => {
    mockHttp = jasmine.createSpyObj('mockHttp', ['get']);
    this.employeeService = new EmployeeService(mockHttp);
    
    TestBed.configureTestingModule({
      imports: [
        CommonModule
      ],
      declarations: [
        EmployeeComponent
      ],
      providers: [
        EmployeeService
      ]
        });
  });

  describe('getEmployees', () => {

    it('should call http.get with the right URL',  () => {
      let result = <IEmployee[]> [];
      mockHttp.get.and.returnValue(Observable.of(true));
      result = this.employeeService.getEmployees();
      expect(mockHttp.get).toHaveBeenCalledWith
      (_employeeUrl);
    });

    it('should get all employees', async(() => {
      let result: IEmployee[];
      let errorMessage: string;
      mockHttp.get.and.returnValue(Observable.of(true));

    }));


  });

});
