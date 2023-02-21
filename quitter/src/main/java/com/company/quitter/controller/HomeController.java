package com.company.quitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/healthcheck")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Server running on port :8080");
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
