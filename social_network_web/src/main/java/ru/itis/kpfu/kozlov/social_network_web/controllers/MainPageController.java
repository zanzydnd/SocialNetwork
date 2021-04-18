package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

import java.util.List;


@Controller
public class MainPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) throws NotFoundException {
        System.out.println(userDetails.getUsername());
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername()));
        List<PostDto> dto = postService.findAll(pageable).getContent();
        System.out.println(dto);
        model.addAttribute("posts", dto);
        return "main";
    }
}
