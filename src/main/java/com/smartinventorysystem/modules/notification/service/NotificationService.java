package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.enums.NotificationType;
import com.smartinventorysystem.modules.notification.dto.response.NotificationResponse;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.user.entity.User;

public interface NotificationService {
    NotificationResponse createNotification(Integer userId,
                                            String title,
                                            String message,
                                            NotificationType type);

    List<NotificationResponse> getAllNotificationsForCurrentUser();

    NotificationResponse markNotificationAsRead(Integer notificationId);

    void markAllNotificationsAsRead();

    void deleteNotification(Integer notificationId);

    long getUnreadNotificationCount();

    void notifyLowStock(Product product);

    void notifyOutOfStock(Product product);

    void notifyReportGenerated(User user, String reportName);
}
