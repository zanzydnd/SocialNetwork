package ru.itis.kpfu.kozlov.social_network_api.services;

import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;

public interface UserService extends CrudService<UserDto,Long> {
    public void register(UserDto userDto);
}
