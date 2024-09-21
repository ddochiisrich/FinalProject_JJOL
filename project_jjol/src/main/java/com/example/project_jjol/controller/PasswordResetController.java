package com.example.project_jjol.controller;

import com.example.project_jjol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PasswordResetController {

    private final UserService userService;

    @Autowired
    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/password-reset")
    public String showPasswordResetForm() {
        return "views/password-reset";
    }

    @PostMapping("/request-password-reset")
    @ResponseBody
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> requestData) {
        String userId = requestData.get("userId");
        String name = requestData.get("name");
        String phone = requestData.get("phone");

        boolean result = userService.requestPasswordReset(userId, name, phone);
        if (result) {
            return ResponseEntity.ok("인증 코드가 전송되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("사용자 정보를 확인해주세요.");
        }
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestData) {
        String userId = requestData.get("userId");
        String phone = requestData.get("phone");
        String verificationCode = requestData.get("verificationCode");
        String newPassword = requestData.get("newPassword");

        boolean result = userService.resetPassword(userId, phone, verificationCode, newPassword);
        if (result) {
            return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증 코드가 일치하지 않거나 만료되었습니다.");
        }
    }
}
