package ru.itis.kpfu.kozlov.social_network_web.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static JwtUser create(UserDto user) throws ParseException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(user.getDateOfBirth());
        Date date = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(user.getDateOfBirth());
       // System.out.println(new Date(user.getDateOfBirth()));
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                date,
                user.getAbout(),
                mapToGrantedAuthority(user.getRole())
        );
    }

    private static GrantedAuthority mapToGrantedAuthority(UserEntity.Role userRole) {
        return new SimpleGrantedAuthority(userRole.name());
    }

    private static GrantedAuthority mapToGrantedAuthority(String userRole) {
        return new SimpleGrantedAuthority(userRole);
    }
}
