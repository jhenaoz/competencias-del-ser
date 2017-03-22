package co.com.psl.evaluacionser.controller;

import co.com.psl.evaluacionser.service.PasswordService;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "api/password")
public class AdministratorController {

    private PasswordService passwordService;

    @Autowired
    public AdministratorController(final PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody Password password,
                                         HttpServletRequest httpServletRequest) {
        return passwordService.updatePassword(password);
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ResponseEntity forgotPassword() {
        return passwordService.forgotPassword();
    }
}
