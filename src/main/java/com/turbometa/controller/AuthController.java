package com.turbometa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/backoffice")
    public String backoffice() {
        return "redirect:/backoffice/turbos";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/backoffice";
    }
}
