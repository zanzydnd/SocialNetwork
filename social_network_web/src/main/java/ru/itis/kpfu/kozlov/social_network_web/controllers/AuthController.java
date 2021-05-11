package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.kpfu.kozlov.social_network_api.dto.AuthenticationRequestDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_web.PasswordChecker;
import ru.itis.kpfu.kozlov.social_network_web.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private PasswordEncoder passwordEncoder;


    private  PasswordChecker passwordChecker;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService
            , PasswordEncoder passwordEncoder , PasswordChecker passwordChecker) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.passwordChecker = passwordChecker;
    }

    @PostMapping("/auth_api/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        System.out.println("allo");
        try {
            Map<Object, Object> response = passwordChecker.checkPassword(requestDto);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            //return new ResponseEntity(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @GetMapping("/auth")
    public String authPage() {
        return "authPage";
    }
}
