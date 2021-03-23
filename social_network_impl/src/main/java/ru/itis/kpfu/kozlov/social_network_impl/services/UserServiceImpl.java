package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void register(UserDto userDto) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(userDto.getDateOfBirth());
            UserEntity user = new UserEntity();
            modelMapper.map(userDto, user);
            user.setId(null);
            user.setDateOfBirth(date);
            user.setState(UserEntity.State.NOT_CONFIRMED);
            user.setRole(UserEntity.Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }

    @Override
    public Optional<UserDto> findById(Long aLong) {
        return userRepository.findById(aLong).map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }

    @Override
    public Boolean save(UserDto userDto) {
        return true;
    }

    @Override
    public Boolean delete(UserDto userDto) {
        return true;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return true;
    }
}
