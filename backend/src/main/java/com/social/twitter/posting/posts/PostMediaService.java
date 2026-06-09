package com.social.twitter.posting.posts;

import com.social.twitter.posting.posts.internal.entity.PostMediaEntity;

import java.util.List;

public interface PostMediaService {

    PostMediaEntity addPostMedia(PostMediaEntity postMediaEntity);

    List<PostMediaEntity> getUsersPostMediasByPostId(Long postId);

    List<PostMediaEntity> getUsersPostMediasByUserId(Long userId);
}