import { Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { SurveyOptionsComponent } from './survey-options/survey-options.component';
import { SurveyComponent } from './survey/survey.component';
import { Error404Component } from './errors/404.component';
import { SurveyRouteActivator } from './survey/survey.route.activator.service';
import { SurveyEndComponent } from './survey-end/survey-end.component';
import { LoginComponent } from './login/login.component';
import { PasswordComponent } from './change-password/change-password.component';

export const appRoutes: Routes = [
    // {path: 'path/to/somewhere', component: ComponentForSomeWhere}
    { path: '', redirectTo: 'welcome', pathMatch: 'full' },
    { path: 'welcome', component: WelcomeComponent },
    { path: 'survey-setup', component: SurveyOptionsComponent },
    { path: 'surveyteam-setup', component: SurveyOptionsComponent },
    { path: 'survey/:id', component: SurveyComponent, canActivate: [SurveyRouteActivator] },
    { path: 'final', component: SurveyEndComponent },
    { path: '404', component: Error404Component },
    { path: 'login', component: LoginComponent },
    { path: 'password/change', component: PasswordComponent }
];
