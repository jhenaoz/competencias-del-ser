import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class SurveyRouteActivator implements CanActivate {

    allow: boolean;

    constructor(private router: Router) {

    }

    canActivate() {
        if (this.allow) {
            this.allow = false;
            return true;
        }else {
            this.router.navigate(['welcome']);
            return false;
        }
    }
}
