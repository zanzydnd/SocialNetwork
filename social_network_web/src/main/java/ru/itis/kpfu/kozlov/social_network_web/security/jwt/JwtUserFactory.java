package ru.itis.kpfu.kozlov.social_network_web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.util.Date;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(UserEntity user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAbout(),
                mapToGrantedAuthority(user.getRole())
        );
    }

    public static JwtUser create(UserDto user){
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                new Date(user.getDateOfBirth()),
                user.getAbout(),
                mapToGrantedAuthority(user.getRole())
        );
    }
    private static GrantedAuthority mapToGrantedAuthority(UserEntity.Role userRole){
        return new SimpleGrantedAuthority(userRole.name());
    }

    private static GrantedAuthority mapToGrantedAuthority(String userRole){
        return new SimpleGrantedAuthority(userRole);
    }
}
