package ru.itis.kpfu.kozlov.social_network_web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

import java.io.IOException;
import java.util.List;


@RestController
public class PostRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/api/posts")
    @ResponseBody
    public ResponseEntity<Page<PostDto>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @PostMapping(value = "/api/posts")
    public ResponseEntity<?> createPost(
            @RequestParam("text") String text,
            @RequestParam("authorId") Long id,
            @RequestParam("address") String address,
            @RequestParam("pathToFile") MultipartFile file
    ) {
        PostDto postDto = new PostDto();
        try {
            postDto = postService.save(text, id, file, address);
        } catch (IOException e) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/api/posts/{post-id}")
    public ResponseEntity<?> deletePost(@PathVariable("post-id") Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/posts/{post-id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("post-id") Long id
            , @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.update(id, postDto));
    }


}
