package ru.itis.kpfu.kozlov.social_network_impl.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "social_network_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String about;
    private String password;
    private Date dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, USER
    }

    public Boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public enum State {
        CONFIRMED, NOT_CONFIRMED, BANNED
    }

    public Boolean isBanned() {
        return this.state == State.BANNED;
    }
}
