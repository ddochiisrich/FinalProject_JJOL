package com.example.project_jjol.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.Notification;
import com.example.project_jjol.repository.NotificationMapper;


@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    // 생성자 주입을 통해 NotificationMapper 의존성 주입
    public NotificationService(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    // 기존 알림 생성 기능
    public void createNotification(Notification notification) {
        notificationMapper.insert(notification);
    }

    // 다가오는 시험 확인 기능 (수정된 부분)
    @Transactional(readOnly = true)
    public List<Notification> getUpcomingNotifications(String userId) {
        LocalDate today = LocalDate.now();
        LocalDate upcomingDate = today.plusDays(7);  // 7일 이내의 다가오는 시험을 확인
        return notificationMapper.findByUsernameAndExamDateBetween(userId, today, upcomingDate);
    }
    
    @Transactional(readOnly = true)
    public Notification getMostUrgentNotification(String userName) {
    	
    	Notification notification = notificationMapper.findMostUrgentNotification(userName);
    	if(notification != null) {
    		int daysUntilExam = notification.getDaysUntilExam();
    	}
    	return notification;
//    	LocalDate today = LocalDate.now();
//    	return notificationMapper.findMostUrgentNotification(userName, today);
    }

	public void notifyNotification() { // 알림 전송 로직
		
	}

	public List<Notification> getNotificationsByUserId(String userId) { // 알림 내역 보기 설정
		
		return null;
	}
}