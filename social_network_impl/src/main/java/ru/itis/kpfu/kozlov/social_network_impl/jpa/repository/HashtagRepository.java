package ru.itis.kpfu.kozlov.social_network_impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itis.kpfu.kozlov.social_network_impl.entities.CommentEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.HashtagEntity;

public interface HashtagRepository extends JpaSpecificationExecutor<HashtagEntity>, JpaRepository<HashtagEntity, Long> {
    boolean existsByName(String name);
    HashtagEntity findByName(String name);
}
