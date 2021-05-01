package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String getPage(@AuthenticationPrincipal UserDetails userDetails) {
        return "adminPage";
    }

}
