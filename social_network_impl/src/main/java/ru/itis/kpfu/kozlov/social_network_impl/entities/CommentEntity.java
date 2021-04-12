package ru.itis.kpfu.kozlov.social_network_impl.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String text;

    @CreationTimestamp
    public Date createdDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity user;

    @ManyToOne
    private PostEntity post;

    @Override
    public String toString() {
        return
                "Comment [id = " + id + ", date = " + createdDate.toString() +
                        ", userId = " + user.getId() + ", postId= " + post.getId() + " ]";
    }
}
