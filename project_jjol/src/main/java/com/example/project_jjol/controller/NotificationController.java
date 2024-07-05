package com.example.project_jjol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.project_jjol.model.Notification;
import com.example.project_jjol.service.NotificationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    // 메인 페이지로 이동할 때 다가오는 시험 확인 (수정된 부분)
    @GetMapping("/")
    public String lectures(Model model) {
        model.addAttribute("upcomingNotifications", notificationService.getUpcomingNotifications());
        return "lectures";  // "templates/lectures.html" 원래 메인페이지로 반환
    }

    @GetMapping("/form")
    public String showNotificationForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "notificationForm";  // "templates/notificationForm.html"를 반환
    }

    @PostMapping
    public String createNotification(@ModelAttribute("notification") Notification notification) {
        notificationService.createNotification(notification);
        return "redirect:/notifications/form";
    }

    @PostMapping("/notify")
    public String notifyNotification(@RequestParam String to, @RequestParam Long notificationId) {
        notificationService.notifyNotification(notificationId, to);
        return "redirect:/notifications/form";
    }
}
