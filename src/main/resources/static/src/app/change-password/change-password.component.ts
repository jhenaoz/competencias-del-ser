import { Component } from '@angular/core';
import { PasswordService } from './change-password.service';
import { Router } from '@angular/router';

@Component({
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css']
})

export class PasswordComponent {
    newPassword: String = '';
    oldPassword: String = '';
    repeatedPassword: String = '';
    error: String = '';


    constructor(private passwordService: PasswordService, private router: Router) { }

    verify() {
        if (this.newPassword === '' || this.oldPassword === '' ||
            this.repeatedPassword === '') {
            this.error = 'No puede haber campos vacios';
            return;
        } else {

            if (this.newPassword !== this.repeatedPassword) {
                this.error = 'Las contraseñas no coinciden';
                return;
            }
            this.error = '';

            const password = {
                'newPassword': this.newPassword,
                'oldPassword': this.oldPassword
            };
            const jsonString: String = JSON.stringify(password);
            this.passwordService.postPasswordRequest(jsonString)
                .subscribe(next => {
                    this.router.navigateByUrl('/login'); // redirect to login after successful password change
                }, error => {
                    this.error = 'error';
                });
        }
    }
}
