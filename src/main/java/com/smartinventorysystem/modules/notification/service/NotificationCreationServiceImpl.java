package com.smartinventorysystem.modules.notification.service;

import com.smartinventorysystem.constants.MessageConstants;
import com.smartinventorysystem.enums.NotificationType;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.notification.entity.Notification;
import com.smartinventorysystem.modules.notification.mapper.NotificationMapper;
import com.smartinventorysystem.modules.notification.repository.NotificationRepository;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class NotificationCreationServiceImpl
        implements NotificationCreationService {


    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Clock clock;


    @Override
    @Transactional
    public void createNotification(
            Integer userId,
            String title,
            String message,
            NotificationType type) {


        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageConstants.USER_NOT_FOUND_WITH_ID + userId
                        )
                );


        Notification notification = new Notification();

        notification.setUserID(user.getUserID());
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(
                LocalDateTime.now(clock)
        );


        Notification savedNotification =
                notificationRepository.save(notification);



        simpMessagingTemplate.convertAndSend(
                "/topic/notifications/" + user.getUserID(),
                notificationMapper.toResponse(savedNotification)
        );
    }
}