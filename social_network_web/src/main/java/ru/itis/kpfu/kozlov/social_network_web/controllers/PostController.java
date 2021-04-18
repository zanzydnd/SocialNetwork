package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

import java.io.IOException;

@Controller
public class PostController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<?> createPost(@RequestParam("text") String text,
                           @RequestParam("authorId") Long id,
                           @RequestParam("pathToFile") MultipartFile file){
        PostDto postDto = new PostDto();
        try {
            postDto = postService.save(text, id, file);
        } catch (IOException e) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(postDto);
    }
}
