import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';
import { AptitudeService } from '../aptitude/aptitude.service';

@Injectable()
export class SurveyRouteActivator implements CanActivate {
    
    constructor(private _aptitudeService: AptitudeService, private router:Router){

    }

    canActivate(route: ActivatedRouteSnapshot){
        const behaviorsExist = !!this._aptitudeService.getBehaviors(route.params['id'])
        if (!behaviorsExist)
            this.router.navigate(['404'])
        return behaviorsExist
    }
}