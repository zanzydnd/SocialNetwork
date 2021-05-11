package ru.itis.kpfu.kozlov.social_network_api.dto;


import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String email;
    private String password;
}
