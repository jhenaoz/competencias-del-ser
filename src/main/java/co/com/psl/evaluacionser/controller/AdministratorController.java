package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.PasswordService;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "/password")
public class AdministratorController {

    private PasswordService passwordService;

    @Autowired
    public AdministratorController(final PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody Password password,
                                         HttpServletRequest httpServletRequest) {
        try {
            httpServletRequest.logout();
        } catch (ServletException e) {
            //TODO log this
        }
        return passwordService.updatePassword(password);
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity forgotPassword() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
