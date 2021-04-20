package ru.itis.kpfu.kozlov.social_network_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private String text;
    private String authorFirstName;
    private Long authorId;
    private String pathToFile;
    private String date;
    private Long id;
    private Long numberOfLikes;
    private Long numberOfReposts;
    private List<CommentDto> comments;
}
