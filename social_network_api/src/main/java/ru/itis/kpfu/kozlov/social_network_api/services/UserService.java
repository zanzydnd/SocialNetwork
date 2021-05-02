package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.kpfu.kozlov.social_network_api.dto.AuthForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.RegForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;

import java.util.Optional;

public interface UserService{
    void register(RegForm userDto);
    Optional<UserDto> getUserById(Long id);
    UserDto findByEmail(String email);
    void signUpAfterOAuth(UserDto userDto);
    void updateUserAfterOAuth(UserDto userDto, String name, String provider);
    void followUser(Long userId, Long followId) throws NotFoundException;
    void unfollowUser(Long userId, Long unfollowId);
    Page<UserDto> getAll(Pageable pageable);
    void ban(Long userId) throws NotFoundException;
}

