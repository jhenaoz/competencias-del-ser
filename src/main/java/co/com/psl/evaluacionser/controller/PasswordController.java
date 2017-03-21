package co.com.psl.evaluacionser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/password")
public class PasswordController {
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public ResponseEntity changePassword() {

        return new ResponseEntity(HttpStatus.OK);
    }
}
