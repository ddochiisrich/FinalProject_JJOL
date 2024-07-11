package com.example.project_jjol.controller;

import com.example.project_jjol.model.User;
import com.example.project_jjol.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String login(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.findById(userId);

        if (user != null && user.getPass().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/lectures";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/lectures";
//    }
}
