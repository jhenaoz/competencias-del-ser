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
import { LoginComponent } from './login/login.component';
import { PasswordComponent } from './change-password/change-password.component';
import { AdminComponent } from './admin/admin.component';

// Service import
import { LoginService } from './login/login.service';
import { SurveyService } from './survey/survey.service';
import { EmployeeService } from './employee/index';
import { AptitudeService } from './aptitude/index';
import { SurveyRouteActivator } from './survey/survey.route.activator.service';
import { PasswordService } from './change-password/change-password.service';

// Local Storage import
import { LocalStorageModule } from 'angular-2-local-storage';

// Translate imports
import { TranslateModule } from 'ng2-translate';
import { TranslateLoader, TranslateStaticLoader } from 'ng2-translate/src/translate.service';

// CSS & Javascript imports
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SurveyEndComponent } from './survey-end/survey-end.component';
import { PopoverModule } from 'ng2-bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    PopoverModule.forRoot(),
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
  declarations: [
    AppComponent,
    EmployeeComponent,
    NavbarComponent,
    FooterComponent,
    WelcomeComponent,
    SurveyOptionsComponent,
    SurveyComponent,
    Error404Component,
    SurveyEndComponent,
    LoginComponent,
    PasswordComponent,
    AdminComponent
  ],
  providers: [
    EmployeeService,
    SurveyService,
    AptitudeService,
    SurveyRouteActivator,
    LoginService,
    PasswordService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
export function translateLoaderFactory(http: any) {
    return new TranslateStaticLoader(http, 'assets/i18n', '.json');
}
