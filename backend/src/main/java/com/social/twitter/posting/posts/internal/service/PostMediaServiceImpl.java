package com.social.twitter.posting.posts.internal.service;


import com.social.twitter.posting.posts.PostMediaService;
import com.social.twitter.posting.posts.internal.entity.PostMediaEntity;
import com.social.twitter.posting.posts.internal.repository.PostMediaRepository;
import com.social.twitter.posting.posts.internal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostMediaServiceImpl implements PostMediaService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMediaRepository postMediaRepository;

    @Override
    public PostMediaEntity addPostMedia(PostMediaEntity postMediaEntity) {
        System.out.println(postMediaEntity.toString());
        return postMediaRepository.save(postMediaEntity);
    }
    @Override
    public List<PostMediaEntity> getUsersPostMediasByPostId(Long postId) {
        return postMediaRepository.findAllByPostEntity(postRepository.findById(postId).get());
    }
    @Override
    public List<PostMediaEntity> getUsersPostMediasByUserId(Long userId) {
        return postMediaRepository.findAllByUserId(userId);
    }

}
