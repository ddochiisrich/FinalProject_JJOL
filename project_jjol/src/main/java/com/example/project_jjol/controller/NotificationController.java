package com.example.project_jjol.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.project_jjol.model.Notification;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.NotificationService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    
    @GetMapping("/notificationList")
    public String showNotifications(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            String userId = loggedInUser.getUserId();
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            model.addAttribute("notifications", notifications);
        }
       return "notificationList";
    }
    
    @GetMapping("/form")
    public String NotificationForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "notificationForm";  // "templates/notificationForm.html"를 반환
    }
    
   
    @PostMapping("/notify")
    public String notifyNotification(Notification notification, HttpSession session) {
    	
    	System.out.println("NotificationController : " + notification.getSubject() + " / " + notification.getUserName());
    	
    	User user = (User)session.getAttribute("loggedInUser");
    	notification.setUserName(user.getUserId());
    	
    	System.out.println(user.getUserId());
    	
        notificationService.createNotification(notification);
        return "redirect:/notifications/form"; // 알림 추가 후 원래 form 페이지로 리다이렉트
    }
}
