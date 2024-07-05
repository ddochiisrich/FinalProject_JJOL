package com.example.project_jjol.service;

import java.time.LocalDate;
import java.util.List;

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

    // 기존 이메일 알림 기능
    public void notifyNotification(Long notificationId, String recipientEmail) {
        Notification notification = notificationMapper.findById(notificationId);
        if (notification == null) {
            throw new IllegalArgumentException("유효하지 않은 알림 ID:" + notificationId);
        }

        String subject = "시험 알림: " + notification.getSubject();
        String text = notification.getSubject() + " 시험이 임박했습니다!";

        emailService.sendSimpleMessage(recipientEmail, subject, text);
    }

    // 기존 알림 생성 기능
    public void createNotification(Notification notification) {
        notificationMapper.insert(notification);
    }

    // 다가오는 시험 확인 기능 (수정된 부분)
    public List<Notification> getUpcomingNotifications() {
        LocalDate today = LocalDate.now();
        LocalDate upcomingDate = today.plusDays(7);  // 7일 이내의 다가오는 시험을 확인
        return notificationMapper.findByExamDateBetween(today, upcomingDate);
    }
}
