package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.PasswordService;
import co.com.psl.evaluacionser.service.dto.Password;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * This controller class gives access to the password services
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.bind.annotation.RequestMethod;
 * import org.springframework.web.bind.annotation.ResponseBody;
 * import org.springframework.web.bind.annotation.RestController;
 * <p>
 * import javax.servlet.http.HttpServletResponse;
 * <p>
 * import java.io.IOException;
 * <p>
 * <p>
 * /**
 * This controller class gives the access to the password services
 */

@RestController
@RequestMapping(value = "/password")
public class PasswordController {

    private static final Logger logger = Logger.getLogger(PasswordController.class);
    private PasswordService passwordService;

    @Autowired
    public PasswordController(final PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * This method receives a post request and allows the administrator to update their own password,
     * it receives in the request a new password and either the old password or a password reset token
     *
     * @param password the object password contains the password to compare and the new password
     * @return a response entity with a OK status if the password was changed or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody  Password password, HttpServletResponse response) {
        if (passwordService.updatePassword(password)) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                logger.error("The endpoint can't redirect to the login page", e);
            }
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method receives a get request and sends a token to the admin's email to change the password
     *
     * @return a response entity with a OK status if the token was sent or a BAD_REQUEST if it was not
     */
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ResponseEntity forgotPassword(HttpServletResponse response) {
        passwordService.forgotPassword();
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            logger.error("The endpoint can't redirect to the main page", e);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
