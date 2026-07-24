package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.modules.notification.dto.response.NotificationResponse;

import java.util.List;


public interface NotificationService {


    List<NotificationResponse> getAllNotificationsForCurrentUser();

    NotificationResponse markNotificationAsRead(
            Integer notificationId
    );

    void markAllNotificationsAsRead();

    void deleteNotification(
            Integer notificationId
    );

    long getUnreadNotificationCount();

}