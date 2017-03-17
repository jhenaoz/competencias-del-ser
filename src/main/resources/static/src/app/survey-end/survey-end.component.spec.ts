import {APP_BASE_HREF} from '@angular/common';
import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';
import { Http } from '@angular/http';

import { ReactiveFormsModule } from '@angular/forms';

import { SurveyEndComponent } from './survey-end.component';
import { SurveyService } from '../survey/survey.service';
import { Survey } from '../survey/survey.model';

import { NavbarComponent } from '../navbar/navbar.component';

import { RouterModule, Router, Routes } from '@angular/router';

class MockRouter {
    navigate(url: string) { return url; }
}

function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, '', '');
}

describe('Component: SurveyEndComponent', () => {
  let component: SurveyEndComponent;
  let fixture: ComponentFixture<SurveyEndComponent>;
  const appRoutes: Routes = [];
  let service: SurveyService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterModule.forRoot(appRoutes),
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: translateLoaderFactory,
            deps: [Http]
        })
      ],
      declarations: [
        SurveyEndComponent,
        NavbarComponent
      ],
      providers: [
        {provide: APP_BASE_HREF, useValue : '/' },
        // { provide: Router, useClass: MockRouter },
        SurveyService
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyEndComponent);
    component = fixture.componentInstance;
    service = TestBed.get(SurveyService);
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
      expect(component).toBeDefined();
  });

  describe('Method: ngOnInit', () => {
    it('should set the value to oneSurvey if service has it true', () => {
      service.oneSurvey = true;
      component.ngOnInit();
      expect(component.oneSurvey).toBeTruthy();
    });
    it('should set the value to oneSurvey if service has it false', () => {
      service.oneSurvey = false;
      component.ngOnInit();
      expect(component.oneSurvey).toBeFalsy();
    });
  });

  describe('Method: evaluateMore', () => {
    it('should create a new survey', () => {
      component.evaluateMore();
      expect(service.survey).toBeDefined();
      expect(service.survey.evaluator).toBe('');
      expect(service.survey.role).toBe('');
      expect(service.survey.evaluated).toBe('');
    });
    it('should navigate to surveyteam-setup', inject([Router], (router: Router) => {
        spyOn(router, 'navigate');
        component.evaluateMore();
        expect(router.navigate).toHaveBeenCalledWith(['/surveyteam-setup']);
    }));
  });

  /*it('should have a defined component', inject([Router], (router: Router) => {
      // expect(component).toBeDefined();
    }));*/

});
