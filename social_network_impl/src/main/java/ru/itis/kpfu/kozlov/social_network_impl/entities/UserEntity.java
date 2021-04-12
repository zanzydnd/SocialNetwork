package ru.itis.kpfu.kozlov.social_network_impl.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.security.AuthProvider;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "social_network_user")
@EqualsAndHashCode(exclude="likedPosts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "PostEntity"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String about;
    private String password;
    private Date dateOfBirth;
    private Boolean isDeleted;

    @ManyToMany(mappedBy = "likes")
    @JsonIgnore
    @JsonView
    private Set<PostEntity> likedPosts;


    @ManyToMany
    @JsonIgnore
    private Set<UserEntity> followedUsers;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private AuthProvider auth_provider;

    public enum AuthProvider {
        LOCAL, GOOGLE
    }

    public enum Role {
        ADMIN, USER
    }

    public Boolean isAdmin() {
        return this.role == Role.ADMIN;
    }


    @Override
    public String toString() {
        return
                "User [id = " + id + ", email = " + email
                        + ", date = " + dateOfBirth.toString() + " ]";
    }
}
