package ru.itis.kpfu.kozlov.social_network_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.kpfu.kozlov.social_network_api.dto.AuthenticationRequestDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
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

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager ,JwtTokenProvider jwtTokenProvider, UserService userService
            , PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            System.out.println("++++");
            String email = requestDto.getEmail();
            System.out.println(email);
            System.out.println(requestDto.getPassword());

            UserDto user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + email + " not found");
            }

            System.out.println(user.getPassword());

            if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
                System.out.println("incorrect password");
                throw new UsernameNotFoundException("Password is incorrect");
            }

            String token = jwtTokenProvider.createToken(email, UserEntity.Role.valueOf(user.getRole()));


            Map<Object, Object> response = new HashMap<>();

            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            System.out.println("123123");
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @GetMapping(value = "/auth")
    public String authPage() {
        return "authPage";
    }
}
