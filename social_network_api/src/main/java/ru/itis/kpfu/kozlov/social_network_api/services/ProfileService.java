package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;

import java.util.List;

public interface ProfileService {
    Page<PostDto> findPostsForProfilePage(Long id, Pageable pageable) throws NotFoundException;
    boolean checkIsUserFollowing(Long userId, Long profileId) throws NotFoundException;
}
