package ru.itis.kpfu.kozlov.social_network_web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.HashtagDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @GetMapping("/hashtag/{hashtagName}")
    public String getHashtagedPosts(@PathVariable String hashtagName,
                                    Pageable pageable,
                                    Model model) {
        System.out.println(hashtagName);
        List<HashtagDto> dto =  postService.findByHashTag("#" + hashtagName, pageable).getContent();
        model.addAttribute("hashtags", dto);
        return "hashtagList";
    }

    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<?> createPost(@RequestParam("text") String text,
                                        @RequestParam("authorId") Long id,
                                        @RequestParam("address")String address,
                                        @RequestParam("pathToFile") MultipartFile file) {
        PostDto postDto = new PostDto();
        try {
            postDto = postService.save(text, id, file,address);
        } catch (IOException e) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(postDto);
    }
}
