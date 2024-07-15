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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

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
        return "views/register";
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

        // 아이디 중복 검사
        if (userService.findById(userId) != null) {
            model.addAttribute("error", "이미 사용중인 아이디입니다.");
            return "views/register";
        }

        // 이메일 중복 검사
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "이미 사용중인 이메일입니다.");
            return "views/register";
        }

        // 핸드폰 번호 중복 검사
        if (userService.findByPhone(phone) != null) {
            model.addAttribute("error", "이미 사용중인 핸드폰 번호입니다.");
            return "views/register";
        }

        User user = new User(userId, password, name, email, phone, role, 0, null, provider);
        userService.saveUser(user);
        return "redirect:/lectures";
    }

    @GetMapping("/checkUserId")
    @ResponseBody
    public Map<String, Boolean> checkUserId(@RequestParam("userId") String userId) {
        boolean isAvailable = userService.findById(userId) == null;
        return Collections.singletonMap("available", isAvailable);
    }

    @GetMapping("/checkEmail")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
        boolean isAvailable = userService.findByEmail(email) == null;
        return Collections.singletonMap("available", isAvailable);
    }

    @GetMapping("/checkPhone")
    @ResponseBody
    public Map<String, Boolean> checkPhone(@RequestParam("phone") String phone) {
        boolean isAvailable = userService.findByPhone(phone) == null;
        return Collections.singletonMap("available", isAvailable);
    }
}
