import { Component } from '@angular/core';
import { PasswordService } from './change-password.service';
import { Router } from '@angular/router';

@Component({
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css']
})

export class PasswordComponent {
    // this attributes are in sync with the html form
    newPassword: String = '';
    oldPassword: String = '';
    repeatedPassword: String = '';
    error: String = '';


    constructor(private passwordService: PasswordService, private router: Router) { }
// this method gets called in the html, makes the password validations and post to the backend
    verifyAndPost() {
        if (this.newPassword === '' || this.oldPassword === '' ||
            this.repeatedPassword === '') {// the passwords can't be empty
            this.error = 'No puede haber campos vacios';
            return;
        } else {

            if (this.newPassword !== this.repeatedPassword) {// the new password must be verified by being typed again
                this.error = 'Las contraseñas no coinciden';
                return;
            }
            this.error = ''; // leaves error blank if passwords are not empty && matching

            const password = {// create an object to conver into json for the post body
                'newPassword': this.newPassword,
                'oldPassword': this.oldPassword
            };
            const jsonString: String = JSON.stringify(password); // the object is parsed
            this.passwordService.postPasswordRequest(jsonString)// here the post method from the service is called
                .subscribe(next => {// subscribe to the post method to check if successful password change
                    this.router.navigateByUrl('/login'); // redirect to login after successful password change
                }, error => {// this checks if an error was thrown by the service
                    this.error = 'Password change error'; // show error on password backend error
                });
        }
    }
}
