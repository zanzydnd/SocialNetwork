package ru.itis.kpfu.kozlov.social_network_impl.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaSpecificationExecutor<UserEntity>, JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByEmail(String email);
}
