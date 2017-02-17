/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs/Rx';

import { 
  EmployeeService,
  EmployeeComponent,
  IEmployee
 } from './index';

describe('EmployeeComponent', () => {
  let component: EmployeeComponent;
  let fixture: ComponentFixture<EmployeeComponent>;
  let mockHttp

  beforeEach(async(() => {
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
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  
  
/*  it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
