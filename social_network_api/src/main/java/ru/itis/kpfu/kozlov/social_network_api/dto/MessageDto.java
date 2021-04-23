package ru.itis.kpfu.kozlov.social_network_api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private String chatId;
    private Long senderId;
    private Long recipientId;
    private String senderFirstName;
    private String recipientFirstName;
    private String text;
    private Date timestamp;
}
