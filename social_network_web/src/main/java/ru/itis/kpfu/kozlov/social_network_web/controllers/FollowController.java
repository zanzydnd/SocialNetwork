package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.FollowDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;

@Controller
public class FollowController {

    @Autowired
    private UserService userService;


    @PostMapping("/follow")
    @ResponseBody
    public ResponseEntity<?> follow(@RequestBody FollowDto followDto) throws NotFoundException {
        //System.out.println(followDto.getFollowId());
        userService.followUser(followDto.getUserId(), followDto.getFollowId());
        return  ResponseEntity.ok().body("q");
    }

    @PostMapping("/unfollow")
    @ResponseBody
    public ResponseEntity<?> unfollow(@RequestBody FollowDto followDto) throws NotFoundException {
        //System.out.println(followDto.getFollowId());
        userService.unfollowUser(followDto.getUserId(), followDto.getFollowId());
        return  ResponseEntity.ok().body("q");
    }

}
