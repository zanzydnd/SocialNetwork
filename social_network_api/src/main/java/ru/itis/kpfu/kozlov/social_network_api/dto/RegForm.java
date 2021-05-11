package ru.itis.kpfu.kozlov.social_network_api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegForm {
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String password2;
    private String about;
}
