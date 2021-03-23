package ru.itis.kpfu.kozlov.social_network_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String about;
    private String password;
    private Long id;
    private String dateOfBirth;
}
