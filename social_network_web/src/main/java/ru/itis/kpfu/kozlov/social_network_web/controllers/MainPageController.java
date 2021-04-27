package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;
import ru.itis.kpfu.kozlov.social_network_web.security.oauth2.CustomOAuth2User;

import java.util.List;


@Controller
public class MainPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getPage(Model model, @AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        UserDto user = null;
        if (userDetails != null) {
            user = userService.findByEmail(userDetails.getUsername());
        } else {
            return "main_page";
        }
        model.addAttribute("user", user);
        List<PostDto> dto = postService.findForMainPage(user.getId(), pageable).getContent();
        System.out.println(dto);
        model.addAttribute("posts", dto);
        return "main";
    }
}
