package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.kpfu.kozlov.social_network_api.dto.MessageDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;

public interface ChatService {
    Page<MessageDto> getAll(Pageable pageable);

    MessageDto save(MessageDto messageDto) throws NotFoundException;

    Page<MessageDto> findForChat(String chatId1, String chatId2, Pageable pageable);
}
