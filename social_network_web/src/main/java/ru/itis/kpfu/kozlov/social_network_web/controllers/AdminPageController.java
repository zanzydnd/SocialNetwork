package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

import java.util.List;

@Controller
public class AdminPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getPage(@AuthenticationPrincipal UserDetails userDetails, Model model, Pageable pageable) {
        List<UserDto> userDtoList = userService.getAll(pageable).getContent();
        userDtoList.forEach(userDto -> userDto.setPassword(null));
        model.addAttribute("userList", userDtoList);
        return "adminPage";
    }

    @GetMapping("/ban/{userId}")
    public String banUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {
        userService.ban(userId);
        return "redirect:/admin";
    }
}
