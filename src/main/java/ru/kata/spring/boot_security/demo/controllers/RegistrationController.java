package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class RegistrationController {
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
