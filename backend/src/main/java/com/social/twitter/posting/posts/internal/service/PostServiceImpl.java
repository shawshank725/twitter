package com.social.twitter.posting.posts.internal.service;

import com.social.twitter.authentication.UserService;
import com.social.twitter.media.MediaService;
import com.social.twitter.notification.NotificationService;
import com.social.twitter.posting.posts.PostService;
import com.social.twitter.posting.posts.internal.entity.PostEntity;
import com.social.twitter.posting.posts.internal.entity.PostMediaEntity;
import com.social.twitter.posting.posts.internal.repository.PostMediaRepository;
import com.social.twitter.posting.posts.internal.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostMediaRepository postMediaRepository;
    private final MediaService mediaService;
    private final NotificationService notificationService;

    @Override
    public PostEntity addPost(PostEntity postEntity) {
        PostEntity savedPost = postRepository.save(postEntity);
        String postText = savedPost.getPostText();
        String[] postTextSplit = postText.split(" ");
        String allowedPattern = "^[A-Za-z0-9_\\p{So}\\p{Cn}\\p{L}\\p{M}]+$";

        List<String> mentionedUsers = new ArrayList<>();

        for (String i : postTextSplit) {
            if (i.startsWith("@") && i.length() > 1) {
                String username = i.substring(1);
                if (username.matches(allowedPattern)) {
                    mentionedUsers.add(username);
                } else {
                    System.out.println("Invalid username mention skipped: " + username);
                }
            }
        }
        for (String mentionedUsername: mentionedUsers){
            long mentionedUserId = userService.getUserIdByUsername(mentionedUsername);
            if (mentionedUserId != 0 && mentionedUserId != postEntity.getUserId()) {
                try {
                    notificationService.createMentionNotification(
                            mentionedUserId, postEntity.getUserId(),
                            postEntity.getPostId()
                    );
                } catch (Exception e) {
                    log.error("Failed to send notification for mention: {}", mentionedUsername, e);
                }
            }
        }
        if (postEntity.getQuotedPostId() !=null) {
            Optional<PostEntity> originalPostEntity = postRepository.findById(postEntity.getQuotedPostId());
            if (originalPostEntity.isPresent() && (!Objects.equals(originalPostEntity.get().getUserId(), postEntity.getUserId()))){
                notificationService.createQuoteNotification(
                        originalPostEntity.get().getUserId(), postEntity.getUserId(),
                        postEntity.getPostId()
                );
            }
        }

        if (postEntity.getReplyToPostId() !=null) {
            Optional<PostEntity> originalPostEntity = postRepository.findById(postEntity.getReplyToPostId());
            if (originalPostEntity.isPresent() && (!Objects.equals(originalPostEntity.get().getUserId(), postEntity.getUserId()))){
                notificationService.createReplyNotification(
                        originalPostEntity.get().getUserId(), postEntity.getUserId(),
                        postEntity.getPostId());
            }
        }
        return savedPost;
    }

    @Override
    public List<PostEntity> getUsersPosts(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public List<PostEntity> getUsersPostsAscendingOrder(Long userId) {
        return postRepository.findAllByUserIdOrderByCreatedAtAsc(userId);
    }

    @Override
    public List<PostEntity> getUsersPostsDescendingOrder(Long userId) {
        return postRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public String deletePostByPostId(Long postId) {
        try {
            List<PostMediaEntity> mediaEntityList = postRepository.findById(postId).get().getMediaList();
            for (PostMediaEntity postMediaEntity: mediaEntityList){
                String mediaUrl = postMediaEntity.getMediaUrl();
                if (mediaUrl.contains("res.cloudinary")){
                    try {
                        mediaService.deleteFile(mediaUrl);
                        System.out.println("successfully deleted the media: " + mediaUrl);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            postMediaRepository.deleteAll(mediaEntityList);
            postRepository.deleteById(postId);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public PostEntity findPostByPostId(Long postId) {
        Optional<PostEntity> result = postRepository.findById(postId);
        return result.orElse(null);
    }

    @Override
    public Long getQuotedCounts(Long postId){
        return postRepository.countQuotes(postId);
    }

    @Override
    public List<PostEntity> getQuotedPosts(Long postId){
        return postRepository.findAllByQuotedPostId(postId);
    }

    @Override
    public List<PostEntity> findRepliedPostsByPostId(Long postId){
        return postRepository.findAllByReplyToPostIdOrderByCreatedAtDesc(postId);
    }

    @Override
    public List<Long> getPostResults(String input){
        return postRepository.findByPostTextContainingIgnoreCase(input).stream()
                .map(PostEntity::getPostId).toList();
    }

    @Override
    public List<Long> getPostIds(Set<Long> userIds) {
        List<Long> postIds = new ArrayList<>();

        for (Long id : userIds) {
            List<Long> userPostIds = getUsersPosts(id)
                    .stream()
                    .map(PostEntity::getPostId)
                    .toList();
            postIds.addAll(userPostIds);
        }

        return postIds;
    }
}
