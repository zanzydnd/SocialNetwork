package ru.itis.kpfu.kozlov.social_network_impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.kpfu.kozlov.social_network_impl.validation.ValidNames;
import ru.itis.kpfu.kozlov.social_network_impl.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidNames(
        message = "password doesn't match",
        password = "password",
        password2 = "password2"
)
public class UserForm {
    private Long id;
    @Email(message = "email is not correct")
    @NotBlank(message = "please enter email")
    private String email;
    private String firstName;
    private String lastName;
    @NotBlank(message = "password cannot be empty")
    @ValidPassword(message = "invalid password")
    private String password;
    @NotBlank(message = "cannot be empty")
    private String password2;
    private String about;
    private String dateOfBirth;
}
