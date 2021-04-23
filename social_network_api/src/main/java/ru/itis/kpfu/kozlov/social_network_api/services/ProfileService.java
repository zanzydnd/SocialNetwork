package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;

import java.util.List;

public interface ProfileService {
    List<PostDto> findPostsForProfilePage(Long id) throws NotFoundException;
    boolean checkIsUserFollowing(Long userId, Long profileId) throws NotFoundException;
}
