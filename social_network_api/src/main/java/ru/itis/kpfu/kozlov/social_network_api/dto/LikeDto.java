package ru.itis.kpfu.kozlov.social_network_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    public Long userId;
    public Long postId;
}
