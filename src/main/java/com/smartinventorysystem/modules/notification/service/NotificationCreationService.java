package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.enums.NotificationType;

public interface NotificationCreationService {

    void createNotification(
            Integer userId,
            String title,
            String message,
            NotificationType type
    );
}