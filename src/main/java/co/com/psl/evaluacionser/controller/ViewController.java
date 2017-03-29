package co.com.psl.evaluacionser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {

    @RequestMapping(value = {"/welcome", "/survey-setup", "/surveyteam-setup", "/survey/{id}", "/final", "/404"},
            method = RequestMethod.GET)
    public String index() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/api/password/change", method = RequestMethod.GET)
    public String forgotPassword() {
        return "changepassword";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
