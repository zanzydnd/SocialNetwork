package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.LikeDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@Controller
public class LikeController {
    @Autowired
    private PostService postService;

    @PostMapping("/likes")
    @ResponseBody
    public ResponseEntity<Integer> likePost(@RequestBody LikeDto likeDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserDto dto = new UserDto();
        dto.setId(likeDto.getUserId());
        return ResponseEntity.ok(postService.likedByUser(likeDto.getPostId(),dto));
    }
}
