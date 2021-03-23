package ru.itis.kpfu.kozlov.social_network_impl.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private UserEntity user;

    private String pathToFile;
}
