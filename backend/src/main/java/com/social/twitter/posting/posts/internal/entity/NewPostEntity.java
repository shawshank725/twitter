package com.social.twitter.posting.posts.internal.entity;

import com.social.twitter.posting.posts.internal.enums.PostTypeEnum;
import com.social.twitter.posting.posts.internal.enums.PostVisibilityEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPostEntity {

    private Long userId;

    @Size(max = 300, message = "post text or content must be less than 301 characters")
    private String postText;

    private Long quotedPostId;

    private Long replyToPostId;

    @Enumerated(EnumType.STRING)
    private PostTypeEnum postType;

    @Enumerated(EnumType.STRING)
    private PostVisibilityEnum visibility;

    private boolean mediaPresent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;
}
