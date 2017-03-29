import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']

})


export class LoginComponent {
  // this attributes are synced with the html form
  username: String;
  password: String;
  error: String = '';

  constructor(private router: Router, private loginService: LoginService) { }

  login(value: any) {
    this
      .loginService
      .login(this.username, this.password)
      .subscribe(next => {
        this.router.navigateByUrl('/'); // login succeed, redirected to welcome page
      }, error => {
        this.error = 'Bad credentials'; // error message
      });
  }
}




