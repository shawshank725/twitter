package com.social.twitter.posting.posts.internal.repository;

import com.social.twitter.posting.posts.internal.entity.PostEntity;
import com.social.twitter.posting.posts.internal.entity.PostMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMediaEntity, Long> {

    List<PostMediaEntity> findAllByPostEntity(PostEntity postEntity);

    List<PostMediaEntity> findAllByUserId(Long userId);
}
