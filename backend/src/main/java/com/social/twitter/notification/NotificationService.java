package com.social.twitter.notification;

import com.social.twitter.notification.internal.entity.NotificationEntity;
import com.social.twitter.notification.internal.enums.NotificationStatus;

import java.util.List;

public interface NotificationService {

    NotificationEntity sendNotification(NotificationEntity notificationEntity);

    NotificationEntity saveNotification(NotificationEntity notificationEntity);

    List<NotificationEntity> getNotificationsByUser(Long userId);

    int getUnreadNotificationsCount(NotificationStatus status, Long userId);

    NotificationEntity markNotificationAsRead(Long notificationId);

    String deleteNotificationEntity(Long notificationId);

    void createMentionNotification(Long notifiedUserId, Long triggeredByUserId, Long postId);
    void createQuoteNotification(Long notifiedUserId, Long triggeredByUserId, Long postId);
    void createLikeNotification(Long notifiedUserId, Long triggeredByUserId, Long postId);
    void createReplyNotification(Long notifiedUserId, Long triggeredByUserId, Long postId);
    void createFollowNotification(Long notifiedUserId, Long triggeredByUserId, Long postId);

}
