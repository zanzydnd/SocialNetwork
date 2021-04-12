package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;

import java.io.IOException;

public interface PostService extends CrudService<PostDto, Long> {
    PostDto save(String text, Long id, MultipartFile multipartFile) throws IOException;
    Integer getAmountOfLikes(Long postId);
    Integer likedByUser(Long postId, UserDto userDto);
}
