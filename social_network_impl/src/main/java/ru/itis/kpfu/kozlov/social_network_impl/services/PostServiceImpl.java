package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

import javax.persistence.criteria.JoinType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(SpecificationUtils.byId(null)
                .and(((root, criteriaQuery, criteriaBuilder) -> {
                    root.fetch("comment", JoinType.LEFT).fetch("user", JoinType.LEFT);
                    root.fetch("author");
                    root.fetch("likes",JoinType.LEFT);
                    return null;
                })), pageable).map(postEntity -> {
            PostDto dto = modelMapper.map(postEntity, PostDto.class);
            dto.setNumberOfLikes((long) postEntity.getLikes().size());
            dto.setComments(postEntity.getComment().stream().map(
                    commentEntity -> {
                        System.out.println(commentEntity.getUser().getEmail());
                        System.out.println(commentEntity.getText());
                        CommentDto dto1 = modelMapper.map(commentEntity, CommentDto.class);
                        dto1.setUserFirstName(commentEntity.getUser().getFirstName());
                        return dto1;
                    }
            ).collect(Collectors.toList()));
            return dto;
        });
    }


    @Override
    public PostDto findById(Long aLong) {
        return postRepository.findById(aLong)
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public PostDto save(PostDto postDto) {
        return null;
    }

    private static final String UPLOAD_DIR = System.getProperty("user.home") + "\\social_network\\img";


    @Override
    public PostDto save(String text, Long id, MultipartFile file) throws IOException {
        PostDto result = new PostDto();
        if (file != null) {
            Date date = new Date();
            long dateString = date.getTime();
            String fileName = UUID.randomUUID().toString() + dateString + file.getOriginalFilename();
            String uploadFilePath = UPLOAD_DIR + "\\" + fileName;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            result.setPathToFile(fileName);
        }
        result.setAuthorId(id);
        result.setText(text);
        result.setAuthorFirstName(userRepository.findById(id).get().getFirstName());
        PostEntity entity = postRepository.save(modelMapper.map(result, PostEntity.class));
        result  = modelMapper.map(entity,PostDto.class);
        result.setAuthorId(id);
        result.setText(text);
        result.setAuthorFirstName(userRepository.findById(id).get().getFirstName());
        return result;
    }

    @Override
    public Integer getAmountOfLikes(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new).getLikes().size();
    }

    @Override
    public Integer likedByUser(Long postId, UserDto userDto) {
        System.out.println("!!!!!!!!!!!!! likeeee");
        UserEntity user = userRepository.findById(userDto.getId())
                .orElseThrow(IllegalArgumentException::new);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new);
        if(postEntity.getLikes().contains(user)){
            postEntity.getLikes().remove(user);
        } else{
            postEntity.getLikes().add(user);
        }
        postRepository.save(postEntity);
        return postEntity.getLikes().size();
    }


    @Override
    public Boolean delete(Long id) {
        PostEntity enitytToDelete = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        enitytToDelete.setIsDeleted(true);
        postRepository.save(enitytToDelete);
        return true;
    }

    @Override
    public PostDto update(Long id, PostDto postDto) {
        PostEntity entityToUpdate = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        entityToUpdate.setText(postDto.getText());
        return modelMapper.map(postRepository.save(entityToUpdate), PostDto.class);
    }

    @Override
    public Boolean deleteById(Long aLong) {
        return null;
    }

    public static class SpecificationUtils {
        public static Specification<PostEntity> byId(Long id) {
            return ((root, criteriaQuery, criteriaBuilder) -> {
                if (id == null) return null;
                return criteriaBuilder.equal(root.get("id"), id);
            });
        }
    }
}
