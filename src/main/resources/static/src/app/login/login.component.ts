import { Component} from '@angular/core';
import { Router } from '@angular/router';
import {Http} from '@angular/http';

import {LoginService} from './login.service'

import { FormGroup, FormControl, Validators, FormBuilder } 
from '@angular/forms' ;

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']

})


export class LoginComponent{

  username:String
  password:String
  error:String = '';
 constructor(private router: Router,private loginService : LoginService){}

  
  
  
login(value: any){
   this
     .loginService
     .login(this.username, this.password)
     .subscribe(next => {
        this.router.navigateByUrl("/"); // login succeed
     }, error => {
        this.error = "Bad credentials"; // or extract smth from <error> object
     });
}
  
}
 



