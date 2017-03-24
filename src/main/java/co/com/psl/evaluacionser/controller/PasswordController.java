package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.PasswordService;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This controller class gives the access to the password services
 */
@CrossOrigin
@RestController
@RequestMapping(value = "api/password")
public class PasswordController {

    private PasswordService passwordService;

    @Autowired
    public PasswordController(final PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * This method receives a post request and allows the administrator to update their own password,
     * this method receives in the request a new password and either the old password or a password reset token
     * @param password the object password contains the password to compare and the new password
     * @return a response entity with a OK status if the password was changed or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity changePassword(Password password) {
        if (passwordService.updatePassword(password)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method receives a get request and sends a token to the admin's email to change the password
     * @return a response entity with a OK status if the token was sent or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ResponseEntity forgotPassword() {
        passwordService.forgotPassword();
        return new ResponseEntity(HttpStatus.OK);
    }
}
