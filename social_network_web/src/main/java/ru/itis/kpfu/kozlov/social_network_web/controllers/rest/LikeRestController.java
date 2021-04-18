package ru.itis.kpfu.kozlov.social_network_web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@RestController
public class LikeRestController {

    @Autowired
    private PostService postService;

    @GetMapping("/api/likes/{postId}")
    public void getAllLikes(@PathVariable Long postId) {
    }

    @GetMapping("/api/likes/{postId}/count")
    public ResponseEntity<Integer> getAmountOfLikes(@PathVariable Long postId) {
        return ResponseEntity.ok(1);
    }


    @PostMapping("/api/likes/{post-id}")
    public ResponseEntity<Integer> likePost(@PathVariable("post-id") Long postId,
                                            @RequestBody UserDto userDto) {
        return ResponseEntity.ok(postService.likedByUser(postId, userDto));
    }
}
