import { EmployeeService } from '../employee';
/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SurveyOptionsComponent } from './survey-options.component';

import { SurveyService } from '../survey/survey.service';
import { SurveyRouteActivator } from '../survey/survey.route.activator.service';

import { AptitudeService } from '../aptitude/aptitude.service';

import { LocalStorageService, LocalStorageModule } from 'angular-2-local-storage';

import { FormGroup, FormControl,  ReactiveFormsModule, FormBuilder, Validators, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';

import { Http } from '@angular/http';

import { EmployeeComponent } from '../employee/employee.component';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';

import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';

import { PopoverModule } from 'ng2-bootstrap';

import {APP_BASE_HREF} from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

declare var jQuery: any;

describe('SurveyOptionsComponent', () => {
  const appRoutes: Routes = [];
  let component: SurveyOptionsComponent;
  let fixture: ComponentFixture<SurveyOptionsComponent>;
  let service: TranslateService;

  let de: DebugElement;
  let el: HTMLElement;

  interface JQuery {
      popover():void;
  }
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
       ReactiveFormsModule,
        PopoverModule.forRoot(),
        RouterModule.forRoot(appRoutes),
        LocalStorageModule.withConfig({
           prefix: 'comp-del-ser',
           storageType: 'localStorage'
       }),
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: translateLoaderFactory,
            deps: [Http]
        })
      ],
      declarations: [ SurveyOptionsComponent, 
                      NavbarComponent, 
                      FooterComponent,
                      EmployeeComponent ],
      providers: [{provide: APP_BASE_HREF, useValue : '/' },
                   SurveyService,
                   AptitudeService,
                   EmployeeService,
                   LocalStorageService,
                   SurveyRouteActivator ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(SurveyOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

    function translateLoaderFactory(http: any) {
      return new TranslateStaticLoader(http, '', '');
    }

    it('should have a defined component', () => {
        expect(component).toBeDefined();
    });

    it('should return true if the form control is valid', () => {
        const formControl = new FormControl('test');
        expect(formControl.valid).toBe(true);
    });

    describe('Method: relationChange()' , () => {
      it('should return isSef true if selfText is equal to value ES' , () => {
        component.selfText = 'Auto-Valoración';
        component.relationChange('Auto-Valoración');
        expect(component.isSelf).toBe(true);
      });
      it('should return isSef true if selfText is equal to value EN' , () => {
        component.selfText = 'Self-assessment';
        component.relationChange('Self-assessment');
        expect(component.isSelf).toBe(true);
      });
       it('should return isClient true if clientText is equal to value ES' , () => {
        component.clientText = 'Cliente';
        component.relationChange('Cliente');
        expect(component.isClient).toBe(true);
      });
      it('should return isClient true if clientText is equal to value ES' , () => {
        component.clientText = 'Client';
        component.relationChange('Client');
        expect(component.isClient).toBe(true);
      });
    });

    describe('Method: reset()' , () => {
      it('should return submitted false' , () => {
        component.submitted = true;
        component.reset();
        expect(component.submitted).toBe(false);
      });
      it('should return isRecent false' , () => {
        component.isRecent = true;
        component.reset();
        expect(component.submitted).toBe(false);
      });
      it('inpuut elements should be enabled' , () => {
        de = fixture.debugElement.query(By.css('input'));
        el = de.nativeElement;
        expect(el.hasAttribute('disabled')).toBe(false);
      });
    });

});
