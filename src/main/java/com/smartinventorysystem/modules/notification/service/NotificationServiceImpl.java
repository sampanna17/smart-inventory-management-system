package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.modules.notification.dto.response.NotificationResponse;
import com.smartinventorysystem.modules.notification.entity.Notification;
import com.smartinventorysystem.modules.notification.mapper.NotificationMapper;
import com.smartinventorysystem.modules.notification.repository.NotificationRepository;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.utils.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotificationsForCurrentUser(){

        Integer currentUserId =
                authenticatedUserProvider.getCurrentUserId();

        return notificationMapper.toResponseList(
                notificationRepository
                        .findAllByUserIDOrderByCreatedAtDesc(currentUserId)
        );

    }

    @Override
    @Transactional
    public NotificationResponse markNotificationAsRead(
            Integer notificationId){

        Integer currentUserId =
                authenticatedUserProvider.getCurrentUserId();

        Notification notification =
                notificationRepository
                        .findByNotificationIDAndUserID(
                                notificationId,
                                currentUserId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with ID: "
                                                + notificationId
                                )
                        );

        notification.setIsRead(true);

        return notificationMapper.toResponse(
                notificationRepository.save(notification)
        );

    }


    @Override
    @Transactional
    public void markAllNotificationsAsRead(){

        Integer currentUserId =
                authenticatedUserProvider.getCurrentUserId();

        List<Notification> notifications =
                notificationRepository
                        .findByUserIDAndIsReadFalseOrderByCreatedAtDesc(
                                currentUserId
                        );

        if(notifications.isEmpty()){
            return;
        }

        notifications.forEach(
                notification ->
                        notification.setIsRead(true)
        );

        notificationRepository.saveAll(notifications);

    }

    @Override
    @Transactional
    public void deleteNotification(
            Integer notificationId){

        Integer currentUserId =
                authenticatedUserProvider.getCurrentUserId();

        Notification notification =
                notificationRepository
                        .findByNotificationIDAndUserID(
                                notificationId,
                                currentUserId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with ID: "
                                                + notificationId
                                )
                        );

        notificationRepository.delete(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount(){

        Integer currentUserId =
                authenticatedUserProvider.getCurrentUserId();

        return notificationRepository
                .countByUserIDAndIsReadFalse(
                        currentUserId
                );

    }
}