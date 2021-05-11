package ru.itis.kpfu.kozlov.social_network_impl.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.itis.kpfu.kozlov.social_network_api.enums.MessageStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private UserEntity recipient;

    @Column(nullable = false)
    private String chatId;


    private String text;

    @CreationTimestamp
    private Date time;
}
