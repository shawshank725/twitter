package com.social.twitter.posting.bookmarks.internal.service;

import com.social.twitter.posting.bookmarks.BookmarkService;
import com.social.twitter.posting.bookmarks.internal.entity.BookmarkEntity;
import com.social.twitter.posting.bookmarks.internal.repository.BookmarkRepository;
import com.social.twitter.posting.likes.internal.entity.LikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Override
    public BookmarkEntity addBookmark(BookmarkEntity bookmarkEntity) {
        return bookmarkRepository.save(bookmarkEntity);
    }

    @Override
    public String removeBookmark(Long bookmarkedByUserId, Long bookmarkedPostId) {
        try {
            Optional<BookmarkEntity> optionalBookmarkEntity = bookmarkRepository.findByBookmarkedPost_PostIdAndBookmarkedByUserId(bookmarkedPostId, bookmarkedByUserId);
            if (optionalBookmarkEntity.isPresent()){
                bookmarkRepository.delete(optionalBookmarkEntity.get());
                return "success";
            }
            else {
                return "not found";
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public List<BookmarkEntity> getAllBookmarksDescendingOrder(Long userId) {
        return bookmarkRepository.findAllByBookmarkedByUserIdOrderByBookmarkedAtDesc(userId);
    }

    @Override
    public List<BookmarkEntity> findAllByBookmarkedPostId(Long postId) {
        return bookmarkRepository.findAllByBookmarkedPost_PostId(postId);
    }

    @Override
    public void deleteAllBookmarksByUserId(Long userId) {
        try {
            List<BookmarkEntity> bookmarkEntities = getAllBookmarksDescendingOrder(userId);
            for (BookmarkEntity bookmarkEntity: bookmarkEntities){
                String result = removeBookmark(userId, bookmarkEntity.getBookmarkedPost().getPostId());
                System.out.println(result);
            }
            System.out.println("Deleted all bookmark entities.");
        }
        catch (Exception e){
            System.out.println("Failed to delete bookmark entities.");
        }
    }
}
