package com.social.twitter.notification.internal.service;

import com.social.twitter.notification.NotificationService;
import com.social.twitter.notification.internal.enums.NotificationType;
import com.social.twitter.notification.internal.entity.NotificationEntity;
import com.social.twitter.notification.internal.enums.NotificationStatus;
import com.social.twitter.notification.internal.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRepository notificationRepository;

    private NotificationEntity createBaseNotification(Long notifiedUserId, Long triggeredByUserId, Long postId){
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationStatus(NotificationStatus.UNREAD);
        notificationEntity.setPostId(postId);
        notificationEntity.setNotificationTime(new Timestamp(System.currentTimeMillis()));
        notificationEntity.setTriggeredByUserId(triggeredByUserId);
        notificationEntity.setNotifiedUserId(notifiedUserId);
        return notificationEntity;
    }
    @Override
    public NotificationEntity sendNotification(NotificationEntity notificationEntity){
        log.info("sending ws notification to {} with payload {}", notificationEntity.getNotifiedUserId(), notificationEntity);
        NotificationEntity savedNotificationEntity = saveNotification(notificationEntity);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(notificationEntity.getNotifiedUserId()),
                "/notification",
                savedNotificationEntity);
        return savedNotificationEntity;
    }

    @Override
    public NotificationEntity saveNotification(NotificationEntity notificationEntity){
        return notificationRepository.save(notificationEntity);
    }

    @Override
    public List<NotificationEntity> getNotificationsByUser(Long userId){
        return notificationRepository.findByNotifiedUserIdOrderByNotificationTimeDesc(userId);
    }

    @Override
    public int getUnreadNotificationsCount(NotificationStatus status, Long userId){
        return notificationRepository.countByNotifiedUserIdAndNotificationStatus(userId,status);
    }

    @Override
    public NotificationEntity markNotificationAsRead(Long notificationId){
        Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findById(notificationId);
        if (notificationEntityOptional.isPresent()){
            NotificationEntity notificationEntity = notificationEntityOptional.get();
            notificationEntity.setNotificationStatus(NotificationStatus.READ);
            return notificationRepository.save(notificationEntity);
        }
        else {
            return null;
        }
    }

    @Override
    public String deleteNotificationEntity(Long notificationId){
        try {
            notificationRepository.deleteById(notificationId);
            return "success";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "failure";
        }
    }

    @Override
    public void deleteAllNotificationsOfUser(Long userId) {
        try {
            List<NotificationEntity> notificationEntities = notificationRepository.findByNotifiedUserIdOrderByNotificationTimeDesc(userId);
            for (NotificationEntity notificationEntity: notificationEntities){
                notificationRepository.delete(notificationEntity);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createMentionNotification(Long notifiedUserId, Long triggeredByUserId, Long postId) {
        NotificationEntity notificationEntity = createBaseNotification(notifiedUserId, triggeredByUserId, postId);
        notificationEntity.setNotificationType(NotificationType.MENTION);
        sendNotification(notificationEntity);
    }

    @Override
    public void createQuoteNotification(Long notifiedUserId, Long triggeredByUserId, Long postId) {
        NotificationEntity notificationEntity = createBaseNotification(notifiedUserId, triggeredByUserId, postId);
        notificationEntity.setNotificationType(NotificationType.QUOTE);
        sendNotification(notificationEntity);
    }

    @Override
    public void createLikeNotification(Long notifiedUserId, Long triggeredByUserId, Long postId) {
        NotificationEntity notificationEntity = createBaseNotification(notifiedUserId, triggeredByUserId, postId);
        notificationEntity.setNotificationType(NotificationType.LIKE);

        sendNotification(notificationEntity);
    }

    @Override
    public void createReplyNotification(Long notifiedUserId, Long triggeredByUserId, Long postId) {
        NotificationEntity notificationEntity = createBaseNotification(notifiedUserId, triggeredByUserId, postId);
        notificationEntity.setNotificationType(NotificationType.REPLY);

        sendNotification(notificationEntity);
    }

    @Override
    public void createFollowNotification(Long notifiedUserId, Long triggeredByUserId, Long postId) {
        NotificationEntity notificationEntity = createBaseNotification(notifiedUserId, triggeredByUserId, postId);
        notificationEntity.setNotificationType(NotificationType.FOLLOW);

        sendNotification(notificationEntity);
    }
}
