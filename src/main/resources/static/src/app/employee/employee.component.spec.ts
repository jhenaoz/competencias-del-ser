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
    // fixture.detectChanges();
    }));

    it('should have a defined component', () => {
        expect(component).toBeDefined();
    });

});
