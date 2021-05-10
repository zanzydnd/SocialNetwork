package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.services.CommentService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/comments")
    @ResponseBody
    public ResponseEntity<CommentDto> save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentDto commentDto) {
        CommentDto dto = commentService.save(commentDto, userDetails.getUsername());
        return ResponseEntity.ok(dto);
    }
}
