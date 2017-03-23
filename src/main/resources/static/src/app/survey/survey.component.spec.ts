/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SurveyComponent } from './survey.component';

import { SurveyService } from './survey.service';
import { SurveyRouteActivator } from './survey.route.activator.service';

import { AptitudeService } from '../aptitude/aptitude.service';

import { LocalStorageService, LocalStorageModule } from 'angular-2-local-storage';

import {
  FormGroup,
  ReactiveFormsModule, FormBuilder, Validators, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';

import { Http } from '@angular/http';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';

import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';

import {APP_BASE_HREF} from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

describe('SurveyComponent', () => {
  const appRoutes: Routes = [];
  let component: SurveyComponent;
  let service: TranslateService;
  let fixture: ComponentFixture<SurveyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
       ReactiveFormsModule,
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
      declarations: [ SurveyComponent, NavbarComponent, FooterComponent ],
      providers: [{provide: APP_BASE_HREF, useValue : '/' },
                   SurveyService,
                   AptitudeService,
                   LocalStorageService,
                   SurveyRouteActivator]
    })
    .compileComponents();
  }));

  function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, '', '');
}


  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

   it('should have a defined component', () => {
        expect(component).toBeDefined();
    });
});
