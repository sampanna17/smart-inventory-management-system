package com.smartinventorysystem.modules.notification.dto.response;

import com.smartinventorysystem.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Integer notificationID;
    private Integer userID;
    private String title;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
