package com.example.project_jjol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.project_jjol.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/chat")
    public String chatGET(HttpSession session, Model model) {
        // 세션에서 loggedInUser 객체를 가져옵니다.
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // 로그인 여부를 확인합니다.
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인이 안 되어있으면 로그인 페이지로 리다이렉트
        }

        // 로그인된 사용자의 이름을 가져옵니다. 또는 userId를 사용할 수 있습니다.
        String id = loggedInUser.getName();
        System.out.println("Logged In User Name: " + loggedInUser.getName());

        // 세션에 id 속성을 설정합니다.
        session.setAttribute("id", id);
        
        // 모델에 id 속성을 추가하여 뷰로 전달합니다.
        model.addAttribute("id", id);
        System.out.println("Model ID: " + id);

        return "views/chat"; // 채팅 페이지로 이동합니다.
    }
}
