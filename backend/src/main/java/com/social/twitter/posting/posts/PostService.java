package com.social.twitter.posting.posts;


import com.social.twitter.posting.posts.internal.entity.PostEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface PostService {

    PostEntity addPost(PostEntity postEntity);

    List<PostEntity> getUsersPosts(Long userId);

    List<PostEntity> getUsersPostsAscendingOrder(Long userId);

    List<PostEntity> getUsersPostsDescendingOrder(Long userId);

    String deletePostByPostId(Long postId);

    PostEntity findPostByPostId(Long postId);

    Long getQuotedCounts(Long postId);

    List<PostEntity> getQuotedPosts(Long postId);

    List<PostEntity> findRepliedPostsByPostId(Long postId);

    List<Long> getPostResults(String input);

    List<Long> getPostIds(Set<Long> userIds);

    Page<PostEntity> getPostsForTimeline(int page, int size);
}