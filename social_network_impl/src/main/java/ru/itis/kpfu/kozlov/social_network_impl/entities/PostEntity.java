package ru.itis.kpfu.kozlov.social_network_impl.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private String pathToFile;
    private Boolean isDeleted;
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private UserEntity author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comment;

    @ManyToMany()
    @JsonView
    @JoinTable(
            name = "post_hashtag",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id")
    )
    private List<HashtagEntity> hashtags;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonView
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<UserEntity> likes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonView
    @JoinTable(
            name = "post_repost",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<UserEntity> reposts;

    @Override
    public String toString() {
        return
                "Post [id = " + id + ", date = " + date.toString() +
                        ", userId = " + author.getId() + ", text= " + text
                        + ", numberOfLikes= " + likes.size() + " ]";
    }
}
