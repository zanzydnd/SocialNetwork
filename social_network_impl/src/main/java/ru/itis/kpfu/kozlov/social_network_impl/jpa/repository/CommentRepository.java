package ru.itis.kpfu.kozlov.social_network_impl.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.itis.kpfu.kozlov.social_network_impl.entities.CommentEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaSpecificationExecutor<CommentEntity>, JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByPost_Id(Long postId, Pageable pageable);
}
