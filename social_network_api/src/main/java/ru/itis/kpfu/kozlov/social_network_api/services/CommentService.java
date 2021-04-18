package ru.itis.kpfu.kozlov.social_network_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;

public interface CommentService {
    Page<CommentDto> findAll(Pageable pageable);
    CommentDto findById(Long id);
    CommentDto save(CommentDto commentDto);
    CommentDto save(CommentDto commentDto,String userEmail);
    void delete(CommentDto commentDto);
    void deleteById(Long id);
    Page<CommentDto> findByPostId(Long postId,Pageable pageable);
}
