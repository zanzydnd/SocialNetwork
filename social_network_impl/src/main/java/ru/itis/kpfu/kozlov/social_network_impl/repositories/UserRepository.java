package ru.itis.kpfu.kozlov.social_network_impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
