package com.example.project_jjol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import com.example.project_jjol.model.Notification;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.NotificationService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private NotificationService notificationService;

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Notification mostUrgentNotification = notificationService.getMostUrgentNotification(loggedInUser.getUserId());
            model.addAttribute("mostUrgentNotification", mostUrgentNotification);
        }
    }
}
