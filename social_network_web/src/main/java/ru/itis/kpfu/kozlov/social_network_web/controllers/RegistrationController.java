package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

import javax.annotation.security.PermitAll;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PermitAll
    @GetMapping("/registration")
    public String registrationPage() {
        return "registrationPage";
    }

    @PermitAll
    @PostMapping("/registration")
    public String registration(UserDto userDto) {
        System.out.println(userDto);
        userService.register(userDto);
        return "redirect:/profile";
    }
}
