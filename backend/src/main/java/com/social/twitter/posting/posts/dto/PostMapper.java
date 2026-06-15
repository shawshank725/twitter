package com.social.twitter.posting.posts.dto;

import com.social.twitter.posting.posts.internal.entity.PostEntity;

import java.util.List;

public class PostMapper {

    public static PostDTO mapPostEntityToPostDTO(PostEntity postEntity) {
        List<PostMediaDTO> mediaList = postEntity.getMediaList()
                .stream()
                .map(media -> new PostMediaDTO(
                        media.getMediaId(),
                        media.getPostEntity().getPostId(),
                        media.getMediaUrl(),
                        media.getUserId(),
                        media.getMediaType()
                ))
                .toList();

        return new PostDTO(
                postEntity.getPostId(),
                postEntity.getUserId(),
                postEntity.getPostText(),
                postEntity.getQuotedPostId(),
                postEntity.getReplyToPostId(),
                postEntity.getPostType(),
                postEntity.getVisibility(),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                mediaList
        );
    }
}