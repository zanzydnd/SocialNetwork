package ru.itis.kpfu.kozlov.social_network_web.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.CommentService;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentRestController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public Page<CommentDto> comments(@PathVariable Long postId, Pageable pageable) {
        return commentService.findByPostId(postId, pageable);
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        commentDto.setPostId(postId);
        System.out.println("!!!!" + commentDto.getUserId());
        CommentDto dto = commentService.save(commentDto);
        System.out.println(dto.getPostId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        commentDto.setId(commentId);
        return commentService.save(commentDto);
    }
}
