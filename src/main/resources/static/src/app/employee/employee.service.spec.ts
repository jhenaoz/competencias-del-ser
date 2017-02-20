/* tslint:disable:no-unused-variable */

/*import { TestBed, async, inject } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs/Rx';

import { 
  EmployeeService,
  EmployeeComponent,
  IEmployee
 } from './index';

describe('EmployeeService', () => {
  let employeeService = EmployeeService
  let mockHttp

  beforeEach(() => {
    mockHttp = jasmine.createSpyObj('mockHttp', ['get'])
    this.employeeService = new EmployeeService(mockHttp)
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

    it('should get all employees',  ()=>{
      let result = <IEmployee[]> []
      result = this.employeeService.getEmployees()
      mockHttp.delete.and.returnValue(Observable.of(false))
      expect(true).toBe(true)
      expect(result.length).toBe(8)
      expect(mockHttp.get).toHaveBeenCalledWith('url')
    })
  })

  it('should ...', inject([EmployeeService], (service: EmployeeService) => {
    expect(service).toBeTruthy();
  }));
});*/
