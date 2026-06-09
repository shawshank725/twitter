package com.social.twitter.posting.bookmarks.internal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.social.twitter.posting.posts.internal.entity.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bookmarked_by_user_id", "bookmarked_post_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"bookmarkedPost"})
@EqualsAndHashCode(exclude = {"bookmarkedPost"})
public class BookmarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "bookmarked_by_user_id", nullable = false)
    private Long bookmarkedByUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmarked_post_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "likes", "mediaList"})
    private PostEntity bookmarkedPost;

    @Column(name = "bookmarked_at", nullable = false)
    private Timestamp bookmarkedAt;
}
