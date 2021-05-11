package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.services.CommentService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.CommentEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.CommentRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<CommentDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CommentDto findById(Long id) {
        return commentRepository.findById(id).map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class))
                .orElseThrow(IllegalAccessError::new);
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        CommentDto comment =  modelMapper.map(commentRepository
                .save(modelMapper.map(commentDto, CommentEntity.class)), CommentDto.class);
        comment.setPostId(commentDto.getPostId());
        comment.setUserFirstName(userRepository.getOne(commentDto.getUserId()).getFirstName());
        return comment;
    }

    @Override
    public CommentDto save(CommentDto commentDto, String userEmail) {
       UserEntity entity =  userRepository.findUserEntityByEmail(userEmail);
       commentDto.setUserId(entity.getId());
       return save(commentDto);
    }

    @Override
    public void delete(CommentDto commentDto) {
        commentRepository.delete(modelMapper.map(commentDto, CommentEntity.class));
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<CommentDto> findByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPost_Id(postId, pageable)
                .map(commentEntity -> modelMapper.map(commentEntity, CommentDto.class));
    }



}
