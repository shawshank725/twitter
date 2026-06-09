package com.social.twitter.posting.posts.internal.entity;

//import com.social.twitter.posting.bookmarks.entity.BookmarkEntity;
//import com.example.posting_service.likes.entity.LikeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.twitter.posting.bookmarks.internal.entity.BookmarkEntity;
import com.social.twitter.posting.likes.internal.entity.LikeEntity;
import com.social.twitter.posting.posts.internal.enums.PostTypeEnum;
import com.social.twitter.posting.posts.internal.enums.PostVisibilityEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@ToString(exclude = {"likes", "bookmarks", "mediaList"})
@EqualsAndHashCode(exclude = {"likes", "bookmarks", "mediaList"})
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_text", length = 300)
    private String postText;

    @Column(name = "quoted_post_id")
    private Long quotedPostId;

    @Column(name = "reply_to_post_id")
    private Long replyToPostId;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false)
    private PostTypeEnum postType;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private PostVisibilityEnum visibility;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<PostMediaEntity> mediaList = new ArrayList<>();

    @OneToMany(mappedBy = "likedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LikeEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "bookmarkedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BookmarkEntity> bookmarks = new ArrayList<>();
}
