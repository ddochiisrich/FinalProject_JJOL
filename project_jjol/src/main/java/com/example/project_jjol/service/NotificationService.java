package com.example.project_jjol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Notification;
import com.example.project_jjol.repository.NotificationMapper;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private EmailService emailService;

    public void notifyNotification(Long notificationId, String recipientEmail) {
        Notification notification = notificationMapper.findById(notificationId);
        if (notification == null) {
            throw new IllegalArgumentException("유효하지 않은 알림 ID:" + notificationId);
        }

        String subject = "시험 알림: " + notification.getSubject();
        String text = notification.getSubject() + " 시험이 임박했습니다!";

        emailService.sendSimpleMessage(recipientEmail, subject, text);
    }

    public void createNotification(Notification notification) {
        notificationMapper.insert(notification);
    }
}

