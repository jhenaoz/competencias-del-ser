import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyEndComponent } from './survey-end.component';

xdescribe('SurveyEndComponent', () => {
  let component: SurveyEndComponent;
  let fixture: ComponentFixture<SurveyEndComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyEndComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


});
