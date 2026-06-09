package com.social.twitter.posting.posts.internal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.social.twitter.posting.posts.internal.enums.PostMediaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="post_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostMediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long mediaId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private PostEntity postEntity;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private PostMediaEnum mediaType;

}