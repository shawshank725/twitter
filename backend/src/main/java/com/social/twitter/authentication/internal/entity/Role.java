package com.social.twitter.authentication.internal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Table(name = "roles")
@Data
@RequiredArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;


    @Column(name = "role_name", unique = true)
    private String roleName;
}
