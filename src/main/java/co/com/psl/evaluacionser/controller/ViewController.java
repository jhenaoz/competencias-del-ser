package co.com.psl.evaluacionser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * this controller redirects the user request to angular 2
 */
@Controller
public class ViewController {

    @RequestMapping(value = {"/welcome", "/survey-setup", "/surveyteam-setup", "/survey/{id}", "/final", "/404",
            "/login", "/password/change"},
            method = RequestMethod.GET)
    public String index() {
        return "forward:/index.html";
    }


}
