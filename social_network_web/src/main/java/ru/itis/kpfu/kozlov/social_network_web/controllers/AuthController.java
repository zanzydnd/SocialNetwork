package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping(value = "/auth")
    public String authPage(){
        return "authPage";
    }
}
