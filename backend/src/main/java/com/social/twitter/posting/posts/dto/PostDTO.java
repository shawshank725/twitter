package com.social.twitter.posting.posts.dto;

import com.social.twitter.posting.posts.internal.enums.PostTypeEnum;
import com.social.twitter.posting.posts.internal.enums.PostVisibilityEnum;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO (
        Long postId,
        Long userId,
        String postText,
        Long quotedPostId,
        Long replyToPostId,
        PostTypeEnum postType,
        PostVisibilityEnum visibility,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<PostMediaDTO> mediaList
){
}
