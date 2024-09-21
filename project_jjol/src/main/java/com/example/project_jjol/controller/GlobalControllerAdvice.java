package com.example.project_jjol.controller;

import com.example.project_jjol.util.StringSanitizerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    // 모든 컨트롤러에 대해 XSS 방어 적용
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 모든 문자열 입력 필드에 대해 StringSanitizerEditor 적용
        binder.registerCustomEditor(String.class, new StringSanitizerEditor());
    }
}
