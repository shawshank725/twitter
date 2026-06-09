package com.social.twitter.posting.posts.internal.entity;

import com.social.twitter.posting.posts.internal.enums.PostMediaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPostMediaEntity {

    private PostEntity postEntity;

    private String mediaUrl;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private PostMediaEnum mediaType;

}
