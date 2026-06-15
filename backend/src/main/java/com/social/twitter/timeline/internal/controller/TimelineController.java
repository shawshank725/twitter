package com.social.twitter.timeline.internal.controller;

import com.social.twitter.authentication.UserService;
import com.social.twitter.posting.posts.PostService;
import com.social.twitter.posting.posts.dto.PostDTO;
import com.social.twitter.posting.posts.internal.entity.PostEntity;
import com.social.twitter.timeline.internal.entity.SearchResult;
import com.social.twitter.timeline.internal.service.TimelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineService timelineService;
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/generate")
    public Page<PostDTO> generatePosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return postService.getPostsForTimeline(page,size);
    }

    @GetMapping("/generateFollowSuggestions")
    public List<Long> generateFollowSuggestions(Long userId) {
        List<Long> followees = timelineService.getFollowees(userId);
        return userService.getSuggestionsForFollowers(followees, userId);
    }

    @GetMapping("/getSearchResult")
    public SearchResult getSearchResult(@RequestParam String input){
        return new SearchResult(
                userService.getUserSearchResult(input),
                postService.getPostResults(input) );
    }
}

