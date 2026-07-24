package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.enums.NotificationType;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.notification.dto.response.NotificationResponse;
import com.smartinventorysystem.modules.notification.entity.Notification;
import com.smartinventorysystem.modules.notification.mapper.notificationMapper;
import com.smartinventorysystem.modules.notification.repository.notificationRepository;
import com.smartinventorysystem.modules.product.entity.Product;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.repository.UserRepository;
import com.smartinventorysystem.utils.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements  NotificationService{

    private final notificationRepository notificationRepository;
    private final notificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Clock clock;

    @Override
    @Transactional
    public NotificationResponse createNotification(Integer userId,
                                                   String title,
                                                   String message,
                                                   NotificationType type) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageConstants.USER_NOT_FOUND_WITH_ID + userId));

        Notification notification = new Notification();
        notification.setUserID(user.getUserID());
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now(clock));

        Notification savedNotification = notificationRepository.save(notification);
        NotificationResponse response = notificationMapper.toResponse(savedNotification);

        simpMessagingTemplate.convertAndSend(
                "/topic/notifications/" + user.getUserID(),
                response
        );

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotificationsForCurrentUser() {

        Integer currentUserId = authenticatedUserProvider.getCurrentUserId();

        return notificationMapper.toResponseList(
                notificationRepository.findAllByUserIDOrderByCreatedAtDesc(currentUserId)
        );
    }

    @Override
    @Transactional
    public NotificationResponse markNotificationAsRead(Integer notificationId) {

        Integer currentUserId = authenticatedUserProvider.getCurrentUserId();

        Notification notification = notificationRepository
                .findByNotificationIDAndUserID(notificationId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with ID: " + notificationId));

        notification.setIsRead(true);

        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void markAllNotificationsAsRead() {

        Integer currentUserId = authenticatedUserProvider.getCurrentUserId();

        List<Notification> unreadNotifications =
                notificationRepository.findByUserIDAndIsReadFalseOrderByCreatedAtDesc(currentUserId);

        if (unreadNotifications.isEmpty()) {
            return;
        }

        unreadNotifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    @Override
    @Transactional
    public void deleteNotification(Integer notificationId) {

        Integer currentUserId = authenticatedUserProvider.getCurrentUserId();

        Notification notification = notificationRepository
                .findByNotificationIDAndUserID(notificationId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification not found with ID: " + notificationId));

        notificationRepository.delete(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount() {

        Integer currentUserId = authenticatedUserProvider.getCurrentUserId();

        return notificationRepository.countByUserIDAndIsReadFalse(currentUserId);
    }

    @Override
    @Transactional
    public void notifyLowStock(Product product) {

        createNotification(
                authenticatedUserProvider.getCurrentUserId(),
                "Low stock alert",
                "Product " + product.getProductName()
                        + " is running low. Current stock: " + product.getStockQuantity()
                        + ", Reorder level: " + product.getReorderLevel(),
                NotificationType.LOW_STOCK
        );
    }

    @Override
    @Transactional
    public void notifyOutOfStock(Product product) {

        createNotification(
                authenticatedUserProvider.getCurrentUserId(),
                "Out of stock alert",
                "Product " + product.getProductName()
                        + " is out of stock.",
                NotificationType.OUT_OF_STOCK
        );
    }

    @Override
    @Transactional
    public void notifyReportGenerated(User user, String reportName) {

        createNotification(
                user.getUserID(),
                "Report generated",
                "Your report \"" + reportName + "\" has been generated successfully.",
                NotificationType.REPORT
        );
    }

}
