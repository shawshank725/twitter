package com.social.twitter.posting.bookmarks;

import com.social.twitter.posting.bookmarks.internal.entity.BookmarkEntity;

import java.util.List;

public interface BookmarkService {

    BookmarkEntity addBookmark(BookmarkEntity bookmarkEntity);

    String removeBookmark(Long bookmarkedByUserId, Long bookmarkedPostId);

    List<BookmarkEntity> getAllBookmarksDescendingOrder(Long userId);

    List<BookmarkEntity> findAllByBookmarkedPostId(Long postId);

    void deleteAllBookmarksByUserId(Long userId);
}
