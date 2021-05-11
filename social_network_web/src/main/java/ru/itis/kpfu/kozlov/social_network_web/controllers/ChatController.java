package ru.itis.kpfu.kozlov.social_network_web.controllers;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.kpfu.kozlov.social_network_api.dto.MessageDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.ChatService;
import ru.itis.kpfu.kozlov.social_network_api.services.UserService;
import ru.itis.kpfu.kozlov.social_network_web.security.details.UserDetailsImpl;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDto messageDto) throws NotFoundException {
        chatService.save(messageDto);
        messagingTemplate.convertAndSendToUser(
                messageDto.getRecipientId().toString(),
                "/queue/messages",
                messageDto
        );
    }

    @GetMapping("/messages/{recipientId}")
    public String usersChatRoom(@PathVariable Long recipientId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model, Pageable pageable) {

        UserDto ourDto = userService.findByEmail(userDetails.getUsername());
        ourDto.setPassword(null);
        UserDto recDto = userService.getUserById(recipientId).get();
        recDto.setPassword(null);

        String chatId1 = ourDto.getId() + "_" + recDto.getId();
        String chatId2 = recDto.getId() + "_" + ourDto.getId();
        model.addAttribute("recipient", recDto);
        model.addAttribute("user", ourDto);
        model.addAttribute("messages", chatService.findForChat(chatId1, chatId2, pageable).getContent());
        return "chat";
    }
}
