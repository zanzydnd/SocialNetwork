package ru.itis.kpfu.kozlov.social_network_api.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthForm {
    private String email;
    private String password;
}
