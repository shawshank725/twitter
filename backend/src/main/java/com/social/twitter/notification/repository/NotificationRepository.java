package com.social.twitter.notification.repository;

import com.social.twitter.notification.entity.NotificationEntity;
import com.social.twitter.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByNotifiedUserIdOrderByNotificationTimeDesc(Long userId);
    int countByNotifiedUserIdAndNotificationStatus(Long userId, NotificationStatus status);
}
