package co.com.psl.evaluacionser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {

    @RequestMapping(value = {"/welcome", "/survey-setup", "/surveyteam-setup", "/survey/{id}", "/final", "/404", "/login"},
            method = RequestMethod.GET)
    public String index() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public String forgotPassword() {
        return "changepassword";
    }

}
