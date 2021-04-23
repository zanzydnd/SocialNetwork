package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.MessageDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.ChatService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.MessageEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.MessageRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<MessageDto> getAll(Pageable pageable) {
        return messageRepository.findAll(
                SpecificationMessageUtils.byId(null)
                        .and(((root, criteriaQuery, criteriaBuilder) -> {
                                    root.fetch("sender");
                                    root.fetch("recipient");
                                    return null;
                                })
                        )
                , pageable).map(messageEntity -> {
            MessageDto dto = modelMapper.map(messageEntity, MessageDto.class);
            dto.setRecipientFirstName(messageEntity.getRecipient().getFirstName());
            dto.setSenderFirstName(messageEntity.getSender().getFirstName());
            return dto;
        });
    }

    @Override
    public MessageDto save(MessageDto messageDto) throws NotFoundException {
        MessageEntity messageEntity = modelMapper.map(messageDto, MessageEntity.class);
        System.out.println(messageDto.getSenderId());
        System.out.println(messageDto);
        messageEntity.setSender(userRepository
                .findById(messageDto.getSenderId()).orElseThrow(NotFoundException::new)
        );
        messageEntity.setRecipient(userRepository.getOne(messageDto.getRecipientId()));

        messageRepository.save(messageEntity);
        return messageDto;
    }

    @Override
    public Page<MessageDto> findForChat(String chatId1, String chatId2, Pageable pageable) {
        return messageRepository.findAll(
                SpecificationMessageUtils
                        .byChatId(chatId1)
                        .or(SpecificationMessageUtils.byChatId(chatId2))
                        .and(((root, criteriaQuery, criteriaBuilder) -> {
                                    root.fetch("sender");
                                    root.fetch("recipient");
                                    return null;
                                })
                        )
                , pageable).map(messageEntity -> {
            MessageDto dto = modelMapper.map(messageEntity, MessageDto.class);
            dto.setRecipientFirstName(messageEntity.getRecipient().getFirstName());
            dto.setSenderFirstName(messageEntity.getSender().getFirstName());
            return dto;
        });
    }


    public static class SpecificationMessageUtils {
        public static Specification<MessageEntity> byId(Long id) {
            return ((root, criteriaQuery, criteriaBuilder) -> {
                if (id == null) return null;
                return criteriaBuilder.equal(root.get("id"), id);
            });
        }

        public static Specification<MessageEntity> byChatId(String chatId) {
            return ((root, criteriaQuery, criteriaBuilder) -> {
                if (chatId == null) return null;
                return criteriaBuilder.equal(root.get("chatId"), chatId);
            });
        }
    }
}
