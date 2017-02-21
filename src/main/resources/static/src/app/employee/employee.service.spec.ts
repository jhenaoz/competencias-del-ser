/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs/Rx';

import { 
  EmployeeService,
  EmployeeComponent,
  IEmployee
 } from './index';



describe('EmployeeService', () => {
  let employeeService: EmployeeService
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

    it('should call http.get with the right URL',  ()=>{
      var result = <IEmployee[]> []
      mockHttp.get.and.returnValue(Observable.of(true))
      result = this.employeeService.getEmployees()
      
      
      expect(mockHttp.get).toHaveBeenCalledWith('https://anypoint.mulesoft.com/apiplatform/proxy/https://mocksvc.mulesoft.com/mocks/d4804468-6192-482e-a2eb-53dca0d66495/person')
    })

    it('should get all employees', async(() =>{
      var result : IEmployee[]
      var errorMessage : string
      mockHttp.get.and.returnValue(Observable.of(true))
      // this._employeeService.getEmployees().subscribe(employees => result = employees, error => errorMessage = <any>error)
      // expect(result).toBe(true)
      // expect(result.length).toBe(8)

      
    }))
       
    
  })


/*  it('should ...', inject([EmployeeService], (service: EmployeeService) => {
    expect(service).toBeTruthy();
  }));*/
});
