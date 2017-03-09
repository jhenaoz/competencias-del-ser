/* tslint:disable:no-unused-variable */
import { TestBed, ComponentFixture, async } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';

import { BrowserModule } from '@angular/platform-browser';

import {
    FormsModule,
    FormGroup,
    ReactiveFormsModule
} from '@angular/forms';

import {EmployeeService} from './employee.service';
import {IEmployee} from './employee.model';
import {EmployeeComponent} from './employee.component';


describe('Component: EmployeeComponent', () => {
  let component: EmployeeComponent;
  let fixture: ComponentFixture<EmployeeComponent>;

  beforeEach(async(() => {
        TestBed.configureTestingModule({
          imports: [
            CommonModule,
            ReactiveFormsModule,
            HttpModule
          ],
          declarations: [
            EmployeeComponent
          ],
          providers: [
            EmployeeService
          ]
        }).compileComponents();


    fixture = TestBed.createComponent(EmployeeComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
    }));

    it('should have a defined component', () => {
        expect(component).toBeDefined();
  });

});
