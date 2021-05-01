package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.exception.NotFoundException;
import ru.itis.kpfu.kozlov.social_network_api.services.ProfileService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
    public Page<PostDto> findPostsForProfilePage(Long id, Pageable pageable) throws NotFoundException {
        return postRepository.findAll(PostServiceImpl.SpecificationUtils.byId(null)
                        .and(((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("author"), id)))
                        .or(
                                ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("reposts").get("id"),id))
                        )
                        .and(((root, criteriaQuery, criteriaBuilder) -> {
                            root.fetch("comment", JoinType.LEFT).fetch("user", JoinType.LEFT);
                            root.fetch("author");
                            root.fetch("likes", JoinType.LEFT);
                            root.fetch("reposts", JoinType.LEFT);
                            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
                            return null;
                        }))
                , pageable)
                .map(postEntity -> {
                    PostDto dto = modelMapper.map(postEntity, PostDto.class);
                    dto.setNumberOfLikes((long) postEntity.getLikes().size());
                    dto.setNumberOfReposts((long) postEntity.getReposts().size());
                    dto.setComments(postEntity.getComment().stream().map(
                            commentEntity -> {
                                System.out.println(commentEntity.getUser().getEmail());
                                System.out.println(commentEntity.getText());
                                CommentDto dto1 = modelMapper.map(commentEntity, CommentDto.class);
                                dto1.setUserFirstName(commentEntity.getUser().getFirstName());
                                return dto1;
                            }
                    ).collect(Collectors.toList()));
                    return dto;
                });
    }

    @Override
    public boolean checkIsUserFollowing(Long userId, Long profileId) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        UserEntity checkEntity = userRepository.findById(profileId).orElseThrow(NotFoundException::new);
        return userEntity.getFollowedUsers().contains(checkEntity);
    }
}
