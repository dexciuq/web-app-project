package com.company.quitter.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "Server running on port :8080";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

}
