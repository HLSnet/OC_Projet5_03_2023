package com.safetynet.SafetynetAlerts.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertController {

    @GetMapping("/")
    public String helloWord(){
        return "Hello world !";
    }

}
