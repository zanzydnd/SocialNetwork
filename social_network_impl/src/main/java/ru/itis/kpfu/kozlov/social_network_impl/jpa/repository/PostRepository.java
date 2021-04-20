package ru.itis.kpfu.kozlov.social_network_impl.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaSpecificationExecutor<PostEntity>, JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByAuthor(UserEntity user);
}
