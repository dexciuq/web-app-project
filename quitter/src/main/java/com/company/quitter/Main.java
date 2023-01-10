package com.company.quitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.format.DateTimeFormatter;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class Main {
    public static final DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
