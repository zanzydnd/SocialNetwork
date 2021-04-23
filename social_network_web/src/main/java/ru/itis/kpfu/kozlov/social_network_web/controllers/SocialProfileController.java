package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.ProfileService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@Controller
public class SocialProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable Long userId, Model model,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) throws NotFoundException {
        model.addAttribute("users_page", userService.getUserById(userId).orElseThrow(NotFoundException::new));
        model.addAttribute("posts_for_profile", profileService.findPostsForProfilePage(userId));
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername()));
        if (model.getAttribute("user") != null) {
            model.addAttribute("isFollowing", profileService.checkIsUserFollowing(
                    ((UserDto) model.getAttribute("user")).getId(), userId
            ));
        }
        return "profile";
    }
}
