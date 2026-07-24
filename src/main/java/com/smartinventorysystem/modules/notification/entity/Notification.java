package com.smartinventorysystem.modules.notification.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;

    private Integer userID;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean isRead = false;

    private LocalDateTime createdAt;
}
