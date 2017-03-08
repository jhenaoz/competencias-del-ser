/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SurveyOptionsComponent } from './survey-options.component';

describe('SurveyOptionsComponent', () => {
  let component: SurveyOptionsComponent;
  let fixture: ComponentFixture<SurveyOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyOptionsComponent ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(SurveyOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

});
