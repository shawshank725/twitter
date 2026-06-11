package com.social.twitter.notification.internal.controller;

import com.social.twitter.notification.NotificationService;
import com.social.twitter.notification.internal.entity.NotificationEntity;
import com.social.twitter.notification.internal.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value = "/sendNotification",
            consumes = "application/json",
            produces = "application/json")
    public NotificationEntity sendNotification(
            @RequestBody NotificationEntity notificationEntity
    ){
        return notificationService.sendNotification(notificationEntity);
    }

    @GetMapping("/getAllNotificationsByUser")
    public List<NotificationEntity> getAllNotificationsByUser(@RequestParam Long userId){
        return notificationService.getNotificationsByUser(userId);
    }

    @Transactional(readOnly = true)
    @GetMapping("/getUnreadNotificationsCount")
    public int getUnreadNotificationsCount(@RequestParam Long userId){
        return notificationService.getUnreadNotificationsCount(NotificationStatus.UNREAD, userId);
    }

    @PostMapping("/markNotificationAsRead")
    public NotificationEntity markNotificationAsRead(@RequestParam  Long notificationId){
        return notificationService.markNotificationAsRead(notificationId);
    }

    @PostMapping("/deleteNotification")
    public String deleteNotification(@RequestParam Long notificationId){
        return notificationService.deleteNotificationEntity(notificationId);
    }
}
