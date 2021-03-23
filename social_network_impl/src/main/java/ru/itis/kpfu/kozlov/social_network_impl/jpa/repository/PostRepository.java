package ru.itis.kpfu.kozlov.social_network_impl.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    Page<PostEntity> findAllByUser(UserEntity user, Pageable pageable);
    List<PostDto> findAllByIdIsNotNull();
}
