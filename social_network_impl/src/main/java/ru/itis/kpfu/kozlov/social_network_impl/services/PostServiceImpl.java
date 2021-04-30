package ru.itis.kpfu.kozlov.social_network_impl.services;

import com.google.common.base.Joiner;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.kpfu.kozlov.social_network_api.dto.CommentDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.HashtagDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.PostDto;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_api.services.PostService;
import ru.itis.kpfu.kozlov.social_network_impl.JsonReader;
import ru.itis.kpfu.kozlov.social_network_impl.aspects.ExecutionTime;
import ru.itis.kpfu.kozlov.social_network_impl.entities.HashtagEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.PostEntity;
import ru.itis.kpfu.kozlov.social_network_impl.entities.UserEntity;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.HashtagRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.PostRepository;
import ru.itis.kpfu.kozlov.social_network_impl.jpa.repository.UserRepository;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @ExecutionTime
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(SpecificationUtils.byId(null)
                        .and(((root, criteriaQuery, criteriaBuilder) -> {
                            root.fetch("comment", JoinType.LEFT).fetch("user", JoinType.LEFT);
                            root.fetch("author");
                            root.fetch("likes", JoinType.LEFT);
                            root.fetch("reposts", JoinType.LEFT);
                            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
                            return null;
                        }))
                , pageable)
                .map(postEntity -> {
                    PostDto dto = modelMapper.map(postEntity, PostDto.class);
                    dto.setNumberOfLikes((long) postEntity.getLikes().size());
                    dto.setNumberOfReposts((long) postEntity.getReposts().size());
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
    @ExecutionTime
    public Page<PostDto> findForMainPage(Long userId, Pageable pageable) {
        return postRepository.findAll(SpecificationUtils.byId(null)
                        .and(((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("author"), userId)))
                        .or(((root, criteriaQuery, criteriaBuilder) -> root.get("author").in(userRepository.findById(userId).get().getFollowedUsers())))
                        .and(((root, criteriaQuery, criteriaBuilder) -> {
                            root.fetch("comment", JoinType.LEFT).fetch("user", JoinType.LEFT);
                            root.fetch("author");
                            root.fetch("likes", JoinType.LEFT);
                            root.fetch("reposts", JoinType.LEFT);
                            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
                            return null;
                        }))
                , pageable)
                .map(postEntity -> {
                    PostDto dto = modelMapper.map(postEntity, PostDto.class);
                    dto.setNumberOfLikes((long) postEntity.getLikes().size());
                    dto.setNumberOfReposts((long) postEntity.getReposts().size());
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
    @ExecutionTime
    public Page<HashtagDto> findByHashTag(String hashtag, Pageable pageable) {
        return hashtagRepository.findAll(SpecificationUtils
                .hashtagByHashtagName(hashtag)
                .and(((root, criteriaQuery, criteriaBuilder) -> {
                    root.fetch("posts", JoinType.LEFT).fetch("author")
                            .getParent().fetch("likes", JoinType.LEFT)
                            .getParent().fetch("reposts", JoinType.LEFT);
                    return null;
                })), pageable).map(hashtagEntity -> {
            HashtagDto dto = new HashtagDto();
            dto.setName(hashtagEntity.getName());
            dto.setPosts(hashtagEntity.getPosts().stream().map(
                    postEntity -> {
                        PostDto postDto = modelMapper.map(postEntity, PostDto.class);
                        postDto.setAuthorFirstName(postEntity.getAuthor().getFirstName());
                        postDto.setNumberOfLikes((long) postEntity.getLikes().size());
                        postDto.setNumberOfReposts((long) postEntity.getReposts().size());
                        return postDto;
                    }
            ).collect(Collectors.toList()));
            return dto;
        });
    }


    @Override
    @ExecutionTime
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
    @ExecutionTime
    public PostDto save(String text, Long id, MultipartFile file, String address) throws IOException {
        PostDto result = new PostDto();
        System.out.println(file);
        if (!file.isEmpty()) {
            Date date = new Date();
            long dateString = date.getTime();
            String fileName = UUID.randomUUID().toString() + dateString + file.getOriginalFilename();
            String uploadFilePath = UPLOAD_DIR + "\\" + fileName;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            result.setPathToFile(fileName);
        }
        if (address != null) {
            try {
                address = GeoCoding.code(address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<HashtagEntity> hashtagEntities = preprocessHashtags(text);
        result.setAuthorId(id);
        result.setText(text);
        result.setAuthorFirstName(userRepository.findById(id).get().getFirstName());
        PostEntity entity = postRepository.save(modelMapper.map(result, PostEntity.class));
        entity.setAddress(address);
        entity.setHashtags(new ArrayList<>(hashtagEntities));
        entity = postRepository.save(entity);
        result = modelMapper.map(entity, PostDto.class);
        result.setAuthorId(id);
        result.setText(text);
        result.setAddress(address);
        result.setAuthorFirstName(userRepository.findById(id).get().getFirstName());
        return result;
    }

    @ExecutionTime
    private List<HashtagEntity> preprocessHashtags(String text) {
        String regex = "\\B(\\#[a-zA-Zа-яА-я]+\\b)(?!;)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<HashtagEntity> hashtagEntities = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group();
            HashtagEntity entity = hashtagRepository.findByName(str);
            if (entity == null) {
                entity = new HashtagEntity();
                entity.setId(null);
                entity.setName(str);
                entity = hashtagRepository.save(entity);
            }
            hashtagEntities.add(entity);
        }
        return hashtagEntities;
    }

    @Override
    @ExecutionTime
    public Integer getAmountOfLikes(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new).getLikes().size();
    }

    @Override
    @ExecutionTime
    public Integer likedByUser(Long postId, UserDto userDto) {
        System.out.println("!!!!!!!!!!!!! likeeee");
        UserEntity user = userRepository.findById(userDto.getId())
                .orElseThrow(IllegalArgumentException::new);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new);
        if (postEntity.getLikes().contains(user)) {
            postEntity.getLikes().remove(user);
        } else {
            postEntity.getLikes().add(user);
        }
        postRepository.save(postEntity);
        return postEntity.getLikes().size();
    }

    @Override
    @ExecutionTime
    public Integer repostedByUser(Long postId, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(IllegalArgumentException::new);
        if (postEntity.getReposts().contains(user)) {
            postEntity.getReposts().remove(user);
        } else {
            postEntity.getReposts().add(user);
        }
        postRepository.save(postEntity);
        return postEntity.getReposts().size();
    }


    @Override
    @ExecutionTime
    public Boolean delete(Long id) {
        PostEntity enitytToDelete = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        enitytToDelete.setIsDeleted(true);
        postRepository.save(enitytToDelete);
        return true;
    }

    @Override
    @ExecutionTime
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
                System.out.println(root.getModel());
                if (id == null) return null;
                return criteriaBuilder.equal(root.get("id"), id);
            });
        }

        public static Specification<HashtagEntity> hashtagByHashtagName(String hashtag) {
            return ((root, criteriaQuery, criteriaBuilder) -> {
                if (hashtag == null) return null;
                return criteriaBuilder.equal(root.get("name"), hashtag);
            });
        }

    }

    public static class GeoCoding {
        public static String code(String address) throws IOException, JSONException {
            final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";
            Map<String, String> params = new HashMap<>();
            params.put("sensor", "false");
            params.put("address", address);
            final String url = baseUrl + '?' + encodeParams(params) + "&key=AIzaSyA073ajNFNsCVAs0oqzkdhRlx8KgAultYI";
            System.out.println(url);
            final JSONObject response = JsonReader.read(url);
            JSONObject location = response.getJSONArray("results").getJSONObject(0);
            location = location.getJSONObject("geometry");
            location = location.getJSONObject("location");
            final double lng = location.getDouble("lng");// долгота
            final double lat = location.getDouble("lat");// широта
            Map<String, String> result = new HashMap<>();
            result.put("lng", String.valueOf(location.getDouble("lng")));
            result.put("lat", String.valueOf(location.getDouble("lat")));
            JSONObject object = new JSONObject(result);
            return object.toString();
        }


        public static String encodeParams(final Map<String, String> params) {
            return Joiner.on('&').join(params.entrySet().stream().map(input -> {
                try {
                    return input.getKey() +
                            '=' +
                            URLEncoder.encode(input.getValue(), "utf-8");
                } catch (final UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
        }
    }
}
