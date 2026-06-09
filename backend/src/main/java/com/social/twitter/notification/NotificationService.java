package com.social.twitter.notification;

import com.social.twitter.notification.entity.NotificationEntity;
import com.social.twitter.notification.enums.NotificationStatus;

import java.util.List;

public interface NotificationService {

    NotificationEntity sendNotification(NotificationEntity notificationEntity);

    NotificationEntity saveNotification(NotificationEntity notificationEntity);

    List<NotificationEntity> getNotificationsByUser(Long userId);

    int getUnreadNotificationsCount(NotificationStatus status, Long userId);

    NotificationEntity markNotificationAsRead(Long notificationId);

    String deleteNotificationEntity(Long notificationId);
}
