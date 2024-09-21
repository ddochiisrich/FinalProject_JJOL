package com.example.project_jjol.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.dto.PhoneVerificationRequestDto;
import com.example.project_jjol.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	
	// 회원가입 시 핸드폰 인증 코드 전송
	@PostMapping("/send-verification-code")
	public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
	    String phoneNumber = request.get("phoneNumber");
	    return userService.sendSmsForSignupVerification(phoneNumber);
	}

	
	// 인증 코드 검증
	@PostMapping("/verify-phone")
	public ResponseEntity<Map<String, String>> verifyPhone(@RequestBody PhoneVerificationRequestDto requestDto) {
	    return userService.verifyPhoneNumber(requestDto);
	}
}
