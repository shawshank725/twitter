package com.social.twitter.orchestration.service;

import com.social.twitter.authentication.UserService;
import com.social.twitter.connections.ConnectionService;
import com.social.twitter.orchestration.OrchestrationService;
import com.social.twitter.posting.bookmarks.BookmarkService;
import com.social.twitter.posting.likes.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrchestrationServiceImpl implements OrchestrationService {

    private final LikeService likeService;
    private final BookmarkService bookmarkService;
    private final ConnectionService connectionService;
    private final UserService userService;

    @Override
    public void deleteUserAccountAndLikesAndConnections(Long userId) {
        try {
            likeService.deleteAllLikesByUserId(userId);
            bookmarkService.deleteAllBookmarksByUserId(userId);
            connectionService.deleteAllConnectionsByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
