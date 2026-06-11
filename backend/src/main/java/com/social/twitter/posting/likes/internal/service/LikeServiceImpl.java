package com.social.twitter.posting.likes.internal.service;

import com.social.twitter.notification.NotificationService;
import com.social.twitter.notification.internal.entity.NotificationEntity;
import com.social.twitter.notification.internal.enums.NotificationStatus;
import com.social.twitter.notification.internal.enums.NotificationType;
import com.social.twitter.posting.likes.LikeService;
import com.social.twitter.posting.likes.internal.entity.LikeEntity;
import com.social.twitter.posting.likes.internal.repository.LikeRepository;
import com.social.twitter.posting.posts.internal.entity.PostEntity;
import com.social.twitter.posting.posts.internal.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    @Override
    public LikeEntity saveLikeEntity(LikeEntity likeEntity) {
        LikeEntity savedLike = likeRepository.save(likeEntity);
        if (likeEntity.getLikedByUserId() != likeEntity.getLikedPost().getUserId()){
            NotificationEntity notificationEntity = notificationService.sendNotification(
                    NotificationEntity.builder()
                            .notificationStatus(NotificationStatus.UNREAD)
                            .notifiedUserId(likeEntity.getLikedPost().getUserId())
                            .postId(likeEntity.getLikedPost().getPostId())
                            .notificationTime(new Timestamp(System.currentTimeMillis()))
                            .notificationType(NotificationType.LIKE)
                            .triggeredByUserId(likeEntity.getLikedByUserId())
                            .build()
            );
            log.info("NOTIFICATION ENTITY - {}", notificationEntity);
        }
        return savedLike;
    }

    @Override
    public LikeEntity getLikeEntityById(Long likeId) {
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findById(likeId);
        return optionalLikeEntity.orElse(null);
    }

    @Override
    public String deleteByLikeEntity(LikeEntity likeEntity) {
        try {
            likeRepository.delete(likeEntity);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    // THIS TO FIND LIKE ENTITY BY POST ID. TO CALCULATE NUMBER OF LIKES A POST HAS
    @Override
    public List<LikeEntity> getLikesByPostId(Long postId) {
        return likeRepository.findAllByLikedPost_PostId(postId);
    }

    // THIS IS TO FIND LIKE ENTITY BY LIKED POST ID AND LIKED BY USER ID
    @Override
    public LikeEntity getLikeEntityByLikedPostIdAndLikedByUserId(Long likedPostId, Long likedByUserid) {
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByLikedPost_PostIdAndLikedByUserId(likedPostId, likedByUserid);
        return optionalLikeEntity.orElse(null);
    }

    // THIS IS TO FIND ALL THE POSTS A USER HAS LIKED
    @Override
    public List<LikeEntity> getLikeEntityByLikedUserId(Long userId) {
        return likeRepository.findAllByLikedByUserId(userId);
    }

    @Override
    public List<PostEntity> getLikedPostEntityByUserId(Long userId) {
        List<PostEntity> posts = new ArrayList<>();
        List<LikeEntity> likes = likeRepository.findAllByLikedByUserId(userId);
        for (LikeEntity likeEntity: likes){
            posts.add(postRepository.findByPostId(likeEntity.getLikedPost().getPostId()));
        }
        return posts;
    }
}
