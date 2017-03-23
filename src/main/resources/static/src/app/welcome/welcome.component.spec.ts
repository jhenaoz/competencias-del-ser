/* tslint:disable:no-unused-variable */
import {APP_BASE_HREF} from '@angular/common';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Http } from '@angular/http';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';

import { LocalStorageService, LocalStorageModule } from 'angular-2-local-storage';

import { ReactiveFormsModule } from '@angular/forms';

import { RouterModule, Routes } from '@angular/router';

import { SurveyService } from '../survey/survey.service';

import { WelcomeComponent } from './welcome.component';

import { FooterComponent } from '../footer/footer.component';
import { NavbarComponent } from '../navbar/navbar.component';

describe('Component: WelcomeComponent', () => {
  let component: WelcomeComponent;
  let fixture: ComponentFixture<WelcomeComponent>;
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
        }),
        LocalStorageModule.withConfig({
           prefix: 'comp-del-ser',
           storageType: 'localStorage'
       }),
      ],
      declarations: [
        WelcomeComponent,
        FooterComponent,
        NavbarComponent
        ],
      providers: [
        {provide: APP_BASE_HREF, useValue : '/' },
        SurveyService,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeComponent);
    component = fixture.componentInstance;
    service = TestBed.get(SurveyService);
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
      expect(component).toBeDefined();
  });

  describe('Method: OneData', () => {
    it('should store value given', () => {
      component.OneData(true);
      expect(service.oneSurvey).toBeTruthy();
      component.OneData(false);
      expect(service.oneSurvey).toBeFalsy();
    });
  });

});

function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, '', '');
}
