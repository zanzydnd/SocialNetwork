package ru.itis.kpfu.kozlov.social_network_api.services;

import ru.itis.kpfu.kozlov.social_network_api.dto.AuthForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.RegForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;

import java.util.Optional;

public interface UserService{
    void register(RegForm userDto);
    Optional<UserDto> getUserById(Long id);
    UserDto findByEmail(String email);
    void signUpAfterOAuth(String email, String name, String lastname, String provider);
    void updateUserAfterOAuth(UserDto userDto, String name, String provider);
}
