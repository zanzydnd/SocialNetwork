package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.ProfileService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PostDto> findPostsForProfilePage(Long id) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);
        Set<PostEntity> repostedPosts = userEntity.getRepostedPosts();
        List<PostEntity> writtenPosts = postRepository.findAllByAuthor(userEntity);
        List<PostEntity> allPosts = new ArrayList<>(writtenPosts);
        allPosts.addAll(repostedPosts);
        return allPosts.stream().map(postEntity ->
                modelMapper.map(postEntity, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean checkIsUserFollowing(Long userId, Long profileId) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        UserEntity checkEntity = userRepository.findById(profileId).orElseThrow(NotFoundException::new);
        return userEntity.getFollowedUsers().contains(checkEntity);
    }
}
