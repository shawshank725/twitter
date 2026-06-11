package com.social.twitter.timeline.internal.service;

import com.social.twitter.connections.ConnectionService;
import com.social.twitter.connections.internal.entity.ConnectionEntity;
import com.social.twitter.posting.posts.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimelineService {

    private final ConnectionService connectionService;
    private final PostService postService;

    public List<Long> generatePosts(Long userId){
        // get the followees of the user
        Set<Long> followees = connectionService
                .getFollowersOfAUser(userId)
                .stream()
                .map(ConnectionEntity::getFolloweeId)
                .collect(Collectors.toSet());

        // get the post ids of the posts made by the user's followees
        List<Long> postIds = postService.getPostIds(followees);
        return postIds;
    }

    public List<Long> getFollowees(Long userId){
        return new ArrayList<>(connectionService.getFollowersOfAUser(userId)).stream().map(ConnectionEntity::getFolloweeId).toList();
    }

}
