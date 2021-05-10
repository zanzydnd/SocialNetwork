package ru.itis.kpfu.kozlov.social_network_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.kozlov.social_network_api.dto.AuthenticationRequestDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_web.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordChecker {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordChecker(JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder){
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<Object, Object> checkPassword(AuthenticationRequestDto requestDto) {
        String email = requestDto.getEmail();
        UserDto user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Password is incorrect");
        }

        String token = jwtTokenProvider.createToken(email, UserEntity.Role.valueOf(user.getRole()));

        Map<Object, Object> response = new HashMap<>();

        response.put("email", email);
        response.put("token", token);
        return response;
    }
}
