/* tslint:disable:no-unused-variable */
import {APP_BASE_HREF} from '@angular/common';
import { async, ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';
import { Http } from '@angular/http';

import { FooterComponent } from './footer.component';

import { Router } from '@angular/router';

class MockRouter {
    navigate(url: string) { return url; }
}

function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, '', '');
}

describe('Component: FooterComponent', () => {
  // const appRoutes: Routes = [];
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        // RouterModule.forRoot(appRoutes),
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: translateLoaderFactory,
            deps: [Http]
        })
      ],
      declarations: [ FooterComponent ],
      providers: [
        {provide: APP_BASE_HREF, useValue : '/' },
        { provide: Router, useClass: MockRouter },
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
        expect(component).toBeDefined();
  });

  describe('Method: changeButtonsText', () => {
    it('should return true given "survey-setup" as url', () => {
      component.currentUrl = '/survey-setup';
      expect(component.changeButtonsText()).toBeTruthy();
    });
    it('should return true given "survey-setup" as url', () => {
      component.currentUrl = '/surveyteam-setup';
      expect(component.changeButtonsText()).toBeTruthy();
    });
    it('should return false given a random url', () => {
      component.currentUrl = '/';
      expect(component.changeButtonsText()).toBeFalsy();
    });
  });

  describe('Method: cancel', () => {

    it('should call Router.navigate(["welcome"]) with "survey-setup" as url', inject([Router], (router: Router) => {
        spyOn(router, 'navigate');
        component.currentUrl = '/survey-setup';
        component.cancel();
        expect(router.navigate).toHaveBeenCalledWith(['welcome']);
    }));
    it('should call Router.navigate(["welcome"]) with "surveyteam-setup" as url', inject([Router], (router: Router) => {
        spyOn(router, 'navigate');
        component.currentUrl = '/surveyteam-setup';
        component.cancel();
        expect(router.navigate).toHaveBeenCalledWith(['welcome']);
    }));
    for (let i = 1; i <= 8; i++) {
        it('should call Router.navigate(["welcome"]) with "survey/' + i + '" as url',
             inject([Router], (router: Router) => {
                spyOn(router, 'navigate');
                component.currentUrl = '/survey/' + i;
                component.cancel();
                expect(router.navigate).toHaveBeenCalledWith(['welcome']);
        }));
    }
  });

  describe('Method: advance', () => {

    it('should call event emitter given "survey-setup" as url', inject([Router], (router: Router) => {
        spyOn(component.startNewSurvey, 'emit');
        component.currentUrl = '/survey-setup';
        component.advance();
        expect(component.startNewSurvey.emit).toHaveBeenCalled();
    }));
    it('should call event emitter given "surveyteam-setup" as url', inject([Router], (router: Router) => {
        spyOn(component.startNewSurvey, 'emit');
        component.currentUrl = '/surveyteam-setup';
        component.advance();
        expect(component.startNewSurvey.emit).toHaveBeenCalled();
    }));
    for (let i = 1; i <= 8; i++) {
        it('should call event emitter given "survey/' + i + '" as url', inject([Router], (router: Router) => {
            spyOn(component.surveyAdvance, 'emit');
            component.currentUrl = '/survey/' + i;
            component.advance();
            expect(component.surveyAdvance.emit).toHaveBeenCalled();
        }));
    }
  });


});

