// Angular imports
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { RouterModule } from '@angular/router';

// Routes import
import { appRoutes } from './routes';

// Components imports
import { AppComponent } from './app.component';
import { EmployeeComponent } from './employee/employee.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { SurveyOptionsComponent } from './survey-options/survey-options.component';
import { SurveyComponent } from './survey/survey.component';
import { Error404Component } from './errors/404.component';

// Service import
import { SurveyService } from './survey/survey.service';
import { EmployeeService } from './employee/index';
import { AptitudeService } from './aptitude/index';
import { SurveyRouteActivator } from './survey/survey.route.activator.service';

//Translate imports
import { TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from "ng2-translate/src/translate.service";

//CSS & Javascript imports
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    ReactiveFormsModule,   
    HttpModule,
    TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: translateLoaderFactory,
            deps: [Http]
        })
  ],
  declarations: [
    AppComponent,
    EmployeeComponent,
    NavbarComponent,
    FooterComponent,
    WelcomeComponent,
    SurveyOptionsComponent,
    SurveyComponent,
    Error404Component
  ],  
  providers: [
    EmployeeService,
    SurveyService,
    AptitudeService,
    SurveyRouteActivator,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
export function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, 'assets/i18n', '.json');
}
