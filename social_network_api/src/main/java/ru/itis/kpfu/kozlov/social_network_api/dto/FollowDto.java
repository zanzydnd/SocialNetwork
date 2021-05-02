package ru.itis.kpfu.kozlov.social_network_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {
    private Long userId;
    private Long followId;
}
