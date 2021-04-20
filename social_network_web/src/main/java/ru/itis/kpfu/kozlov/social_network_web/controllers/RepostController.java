package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.LikeDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@Controller
public class RepostController {

    @Autowired
    private PostService postService;

    @PostMapping("/repost")
    @ResponseBody
    public ResponseEntity<Integer> repostByUser(@RequestBody LikeDto likeDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.repostedByUser(likeDto.getPostId(),likeDto.getUserId()));
    }
}
