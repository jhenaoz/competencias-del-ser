import { Component } from '@angular/core';
import { PasswordService } from './change-password.service';
import { Router } from '@angular/router';

@Component({
    templateUrl: './change-password.component.html',
    styleUrls: ['../navbar/navbar.component.css', './change-password.component.css']
})

export class PasswordComponent {
    // this attributes are in sync with the html form
    newPassword: String = '';
    oldPassword: String = '';
    repeatedPassword: String = '';
    error = false;


    constructor(private passwordService: PasswordService, private router: Router) { }
// this method gets called in the html, makes the password validations and post to the backend
    postPasswordChangeRequest() {
           this.error = false; // leaves error blank if passwords are not empty && matching

            const password = {// create an object to conver into json for the post body
                'newPassword': this.newPassword,
                'oldPassword': this.oldPassword
            };
            const jsonString: String = JSON.stringify(password); // the object is parsed
            this.passwordService.postPasswordRequest(jsonString)// here the post method from the service is called
                .subscribe(next => {// subscribe to the post method to check if successful password change
                    this.router.navigateByUrl('/login'); // redirect to login after successful password change
                }, error => {// this checks if an error was thrown by the service
                    this.error = true; // show error on password backend error
                });
        }
    }
