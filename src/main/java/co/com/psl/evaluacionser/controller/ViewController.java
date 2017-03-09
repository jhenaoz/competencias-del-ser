package co.com.psl.evaluacionser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/welcome", "/survey-setup", "/surveyteam-setup", "/survey/{id}", "/final", "/404"})
public class ViewController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "forward:/index.html";
    }
}
