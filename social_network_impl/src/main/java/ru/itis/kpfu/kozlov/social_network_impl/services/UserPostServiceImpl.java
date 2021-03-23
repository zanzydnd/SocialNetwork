package ru.itis.kpfu.kozlov.social_network_impl.services;

import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;

import java.util.Optional;

@Service
public class UserPostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public UserPostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postEntity -> modelMapper.map(postEntity, PostDto.class));
    }

    @Override
    public Optional<PostDto> findById(Long aLong) {
        return postRepository.findById(aLong).map(postEntity -> modelMapper.map(postEntity, PostDto.class));
    }

    @Override
    public Boolean save(PostDto postDto) {
        postDto.setId(null);
        postRepository.save(modelMapper.map(postDto, PostEntity.class));
        return true;
    }

    @Override
    public Boolean delete(PostDto postDto) {
        postRepository.delete(modelMapper.map(postDto, PostEntity.class));
        return true;
    }

    @Override
    public Boolean deleteById(Long aLong) {
        postRepository.deleteById(aLong);
        return true;
    }

    public Page<PostDto> findAllByUser(UserEntity user, Pageable pageable) {
        if (user != null) {
            return postRepository.findAllByUser(user, pageable).map(postEntity
                    -> modelMapper.map(postEntity, PostDto.class));
        }
        else{
            return this.findAll(pageable);
        }
    }
}
