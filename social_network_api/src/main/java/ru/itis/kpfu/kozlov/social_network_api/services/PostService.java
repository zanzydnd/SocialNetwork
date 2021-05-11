package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.HashtagDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;

import java.io.IOException;
import java.util.List;

public interface PostService extends CrudService<PostDto, Long> {
    PostDto save(String text, Long id, MultipartFile multipartFile, String address) throws IOException;
    Integer getAmountOfLikes(Long postId);
    Integer likedByUser(Long postId, UserDto userDto);
    Integer repostedByUser(Long postId, Long userId);
    Page<HashtagDto> findByHashTag(String hashtag, Pageable pageable);
    Page<PostDto> findForMainPage(Long userId,Pageable pageable);
}
