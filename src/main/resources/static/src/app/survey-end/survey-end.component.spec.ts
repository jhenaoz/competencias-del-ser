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

xdescribe('Component: SurveyEndComponent', () => {
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
        { provide: Router, useClass: MockRouter },
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


});
