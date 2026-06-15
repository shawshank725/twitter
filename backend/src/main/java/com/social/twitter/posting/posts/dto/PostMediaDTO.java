package com.social.twitter.posting.posts.dto;

import com.social.twitter.posting.posts.internal.enums.PostMediaEnum;

public record PostMediaDTO(
        Long mediaId,
        Long postId,
        String mediaUrl,
        Long userId,
        PostMediaEnum mediaType
) {}