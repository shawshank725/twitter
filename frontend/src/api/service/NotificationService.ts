import axios from 'axios';
import  { SPRING_BOOT_LOCALHOST } from '@constants/MiscConstants';

export async function getUsersNotifications(userId: number) {
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/notifications/getAllNotificationsByUser`, {
        params: {userId},
        withCredentials: true
    });
}

export async function getUnreadNotificationsCount(userId: number){
    return await axios.get(`${SPRING_BOOT_LOCALHOST}/notifications/getUnreadNotificationsCount`, {
        params: {userId},
        withCredentials: true
    });
}

export async function markNotificationAsRead(notificationId: number) {
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/notifications/markNotificationAsRead`,
        {},
        {
            params: { notificationId },
        withCredentials: true
        }
    );
}

export async function deleteNotification(notificationId: number) {
    return await axios.post(
        `${SPRING_BOOT_LOCALHOST}/notifications/deleteNotification`,
        {}, 
        {
            params: { notificationId },
        withCredentials: true
        }
    );
}
