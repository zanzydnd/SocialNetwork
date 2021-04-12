package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T,ID> {
    Page<T> findAll(Pageable pageable);
    T findById(ID id);
    T save(T t);
    Boolean delete(ID id);
    T update(ID id,T t);
    Boolean deleteById(ID id);
}
