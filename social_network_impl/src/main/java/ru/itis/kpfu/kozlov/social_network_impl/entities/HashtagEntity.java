package ru.itis.kpfu.kozlov.social_network_impl.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"posts"})
@Table(name = "hashtag")
public class HashtagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "hashtags", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonView
    private List<PostEntity> posts;
}
