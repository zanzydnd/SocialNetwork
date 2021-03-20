package ru.itis.kpfu.kozlov.social_network_api.services;

public interface UserService<UserDto, Long> {
    public void register(UserDto userDto);
}
