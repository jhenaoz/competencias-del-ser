/* tslint:disable:no-unused-variable */
import { TestBed, ComponentFixture, async } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

import { HttpModule } from '@angular/http';

import {
    ReactiveFormsModule
} from '@angular/forms';

import {EmployeeService} from './employee.service';
import {IEmployee} from './employee.model';
import {EmployeeComponent} from './employee.component';


describe('Component: EmployeeComponent', () => {
  let component: EmployeeComponent;
  let fixture: ComponentFixture<EmployeeComponent>;
  let service: EmployeeService;

  beforeEach(async(() => {
        TestBed.configureTestingModule({
          imports: [
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
    service = TestBed.get(EmployeeService);
    // fixture.detectChanges();
    }));

    it('should have a defined component', () => {
        expect(component).toBeDefined();
    });

     xit('should create a list of employees', async(() => {

      expect(true).toBeTruthy();
    }));

});
