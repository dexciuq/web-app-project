package com.company.quitter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/healthcheck")
    public String healthCheck() {
        return "Server running on port :8080";
    }

}
