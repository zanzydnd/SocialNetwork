package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.AuthForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.RegForm;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;
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
    public void register(RegForm regForm) {
        Date date = new Date();
        try {
            if (userRepository.findUserEntityByEmail(regForm.getEmail()) == null) {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(regForm.getDateOfBirth());
                UserEntity user = new UserEntity();
                modelMapper.map(regForm, user);
                user.setId(null);
                user.setDateOfBirth(date);
                user.setRole(UserEntity.Role.USER);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setFirstName(regForm.getFirstName());
                user.setLastName(regForm.getLastName());
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Username already exists");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity entity = userRepository.findUserEntityByEmail(email);
        if(entity == null){
            return null;
        }
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public void signUpAfterOAuth(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAuth_provider(UserEntity.AuthProvider.valueOf(userDto.getAuthProvider()));
        userRepository.save(user);
    }

    @Override
    public void updateUserAfterOAuth(UserDto userDto, String name, String provider) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        if (provider.equals(UserEntity.AuthProvider.GOOGLE.toString())) {
            userEntity.setAuth_provider(UserEntity.AuthProvider.GOOGLE);
        } else {
            userEntity.setAuth_provider(UserEntity.AuthProvider.LOCAL);
        }
        userEntity.setFirstName(name);
        userRepository.save(userEntity);
    }


}
