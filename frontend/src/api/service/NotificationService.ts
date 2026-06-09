import axios from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';

const NotificationServiceEndpoint = "notification-service";
const notificationsEndpoint = "notifications";
const getAllNotificationsByUserEndpoint = "getAllNotificationsByUser";
const getUnreadNotificationsCountEndpoint = "getUnreadNotificationsCount";
const markNotificationAsReadEndpoint = "markNotificationAsRead";
const deleteNotificationEndpoint = "deleteNotification";

export async function getUsersNotifications(userId: number) {
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/${NotificationServiceEndpoint}/${notificationsEndpoint}/${getAllNotificationsByUserEndpoint}`, {
        params: {userId},
        withCredentials: true
    });
}

export async function getUnreadNotificationsCount(userId: number){
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/${NotificationServiceEndpoint}/${notificationsEndpoint}/${getUnreadNotificationsCountEndpoint}`, {
        params: {userId},
        withCredentials: true
    });
}

export async function markNotificationAsRead(notificationId: number) {
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/${NotificationServiceEndpoint}/${notificationsEndpoint}/${markNotificationAsReadEndpoint}`,
        {},
        {
            params: { notificationId },
        withCredentials: true
        }
    );
}

export async function deleteNotification(notificationId: number) {
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/${NotificationServiceEndpoint}/${notificationsEndpoint}/${deleteNotificationEndpoint}`,
        {}, 
        {
            params: { notificationId },
        withCredentials: true
        }
    );
}
