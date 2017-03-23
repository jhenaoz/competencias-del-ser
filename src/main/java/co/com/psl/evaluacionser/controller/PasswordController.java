package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.PasswordService;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



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
     * This method receives a post request and allows to the administrator updates the own password
     * @param password the object password contains the password to compare and the new password
     * @return a response entity with a OK status if the password was changed or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody Password password) {
        return passwordService.updatePassword(password);
    }

    /**
     * This method receives a get request and sends a token to change the password
     * @return a response entity with a OK status if the token was sent or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ResponseEntity forgotPassword() {
        return passwordService.forgotPassword();
    }
}
