package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_impl.services.UserPostServiceImpl;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public PostDto post(@PathVariable Long id) throws Exception {
        return postService.findById(id).orElseThrow(() -> new Exception("NotFound"));
    }

    @PostMapping
    public void save(@RequestBody PostDto postDto){
        postService.save(postDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        postService.deleteById(id);
    }

}
