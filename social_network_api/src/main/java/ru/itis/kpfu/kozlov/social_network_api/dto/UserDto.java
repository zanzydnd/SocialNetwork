package ru.itis.kpfu.kozlov.social_network_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String about;
    private Long id;
    private String role;
    private String password;
    private String dateOfBirth;
    private String authProvider;
}
