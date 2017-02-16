import { Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { SurveyOptionsComponent } from './survey-options/survey-options.component';
import { SurveyComponent } from './survey/survey.component';

export const appRoutes:Routes = [
    // {path: 'path/to/somewhere', component: ComponentForSomeWhere}
    { path: '', redirectTo: 'welcome', pathMatch: 'full' }, 
    { path: 'welcome', component: WelcomeComponent },
    { path: 'survey-setup', component: SurveyOptionsComponent },
    { path: 'survey', component: SurveyComponent }     
]