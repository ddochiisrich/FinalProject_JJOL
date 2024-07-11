package com.example.project_jjol.controller;



import java.time.LocalDate;
import java.util.Comparator;
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
    
    
 // 여기서 수정 로직을 처리합니다.
    // id를 사용하여 DB에서 해당 알림을 가져와서 수정할 수도 있습니다.
    @PostMapping("/update")
    public String updateNotification(@RequestParam("id") Long id,
                                     @RequestParam("subject") String subject,
                                     @RequestParam("examDate") LocalDate examDate) {
        notificationService.updateNotification(id, subject, examDate);
        return "redirect:/notifications/form"; // 수정 후 폼 페이지로 리다이렉트
    }
    
    // 알림 목록을 보여주는 폼 페이지
    @GetMapping("/form")
    public String showNotificationForm(Model model, HttpSession session, @RequestParam(name = "sort", defaultValue = "default") String sort) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            String userId = loggedInUser.getUserId();
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            
            // 정렬 옵션에 따라 알림 목록 정렬
            switch (sort) {
                case "subject":
                    notifications.sort(Comparator.comparing(Notification::getSubject));
                    break;
                case "examDate":
                    notifications.sort(Comparator.comparing(Notification::getExamDate));
                    break;
                case "id":
                    notifications.sort(Comparator.comparing(Notification::getId));
                    break;
                default:
                    // 기본 정렬: 번호 순으로
                    notifications.sort(Comparator.comparing(Notification::getId));
                    break;
            }
            
            model.addAttribute("notifications", notifications); // 알림 목록을 모델에 추가
            model.addAttribute("notification", new Notification()); // 알림 생성 폼을 위한 객체
            model.addAttribute("sort", sort); // 현재 정렬 기준을 Thymeleaf에 전달
        }
        return "views/notificationForm"; 
    }
    
   
    @PostMapping("/notify")
    public String notifyNotification(@ModelAttribute("Notification") Notification notification, HttpSession session) {
    	
    	System.out.println("NotificationController : " + notification.getSubject() + " / " + notification.getUserName());
    	
    	User user = (User)session.getAttribute("loggedInUser");
    	notification.setUserName(user.getUserId());
    	
    	System.out.println(user.getUserId());
    	
        notificationService.createNotification(notification);
        return "redirect:/notifications/form"; // 알림 추가 후 원래 폼 페이지로 redirect
    }
    
    @PostMapping("/delete")
    public String deleteNotification(@RequestParam("id") Long id) {
    	notificationService.deleteNotificationById(id);
    	return "redirect:/notifications/form"; // 삭제 후 폼 페이지로 redirect

    }
}
