package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudService<T,ID> {
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(ID id);
    Boolean save(T t);
    Boolean delete(T t);
    Boolean deleteById(ID id);
}
