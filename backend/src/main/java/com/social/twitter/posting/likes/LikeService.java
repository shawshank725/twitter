package com.social.twitter.posting.likes;


import com.social.twitter.posting.likes.internal.entity.LikeEntity;
import com.social.twitter.posting.posts.internal.entity.PostEntity;

import java.util.List;

public interface LikeService {

    LikeEntity saveLikeEntity(LikeEntity likeEntity);

    LikeEntity getLikeEntityById(Long likeId);

    String deleteByLikeEntity(LikeEntity likeEntity);

    List<LikeEntity> getLikesByPostId(Long postId);

    LikeEntity getLikeEntityByLikedPostIdAndLikedByUserId(Long likedPostId, Long likedByUserid);

    List<LikeEntity> getLikeEntityByLikedUserId(Long userId);

    List<PostEntity> getLikedPostEntityByUserId(Long userId);

    void deleteAllLikesByUserId(Long userId);
}
