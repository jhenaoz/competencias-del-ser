/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

import { Http } from '@angular/http';

import { TranslateService, TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';

import { ReactiveFormsModule } from '@angular/forms';

import { NavbarComponent } from './navbar.component';

describe('Component: NavbarComponent', () => {
  let component: NavbarComponent;
  let service: TranslateService;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: translateLoaderFactory,
            deps: [Http]
        })
      ],
      declarations: [ NavbarComponent ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    service = TestBed.get(TranslateService);
  });

  it('should have a defined component', () => {
        expect(component).toBeDefined();
  });

  describe('Method: changeLang', () => {
    it('should change language to EN', () => {
      spyOn(service, 'use').and.returnValue({});
      component.changeLang('en');
      fixture.detectChanges();
      expect(service.use).toHaveBeenCalledWith('en');
    });

    it('should change language to ES', () => {
      spyOn(service, 'use').and.returnValue({});
      component.changeLang('es');
      fixture.detectChanges();
      expect(service.use).toHaveBeenCalledWith('es');
    });

  });

});

function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, '', '');
}
