package com.example.project_jjol.controller;

import com.example.project_jjol.model.User;
import com.example.project_jjol.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "소셜 네트워크 아이디로 회원가입이 필요합니다.");
            model.addAttribute("email", session.getAttribute("socialEmail"));
            model.addAttribute("name", session.getAttribute("socialName"));
            model.addAttribute("provider", session.getAttribute("socialProvider"));
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("role") String role,
            @RequestParam(value = "provider", required = false, defaultValue = "normal") String provider,
            Model model) {

        User user = new User(userId, password, name, email, phone, role, 0, null, provider);
        userService.saveUser(user);
        return "redirect:/lectures";
    }
}
