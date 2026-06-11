package com.social.twitter.authentication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Email
    @Size(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 160)
    @Column(name = "bio")
    private String bio;

    @Size(max = 100)
    @Column(name = "website")
    private String website;

    @Size(max = 30)
    @Column(name = "location")
    private String location;

    @NotBlank
    @Size(min = 6, max = 60)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "background_photo")
    private String backgroundPhoto;

    @Column(name = "joined_date", updatable = false, insertable = false)
    private LocalDate joinedDate;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role role;

}