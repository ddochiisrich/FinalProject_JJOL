package com.example.project_jjol.controller;

import com.example.project_jjol.model.User;
import com.example.project_jjol.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String login() {
        return "views/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password) {

        Map<String, String> response = new HashMap<>();

        User user = userService.findById(userId);

        if (user == null) {
            response.put("error", "존재하지 않는 아이디입니다.");
            response.put("field", "userId");
            return response;
        }

        if (!user.getPass().equals(password)) {
            response.put("error", "비밀번호가 일치하지 않습니다.");
            response.put("field", "password");
            return response;
        }

        // 로그인 성공 시 세션에 사용자 정보 저장
        session.setAttribute("loggedInUser", user);
        response.put("status", "success");
        return response;
    }
}
