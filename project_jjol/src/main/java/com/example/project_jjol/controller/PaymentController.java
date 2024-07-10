package com.example.project_jjol.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
	
	@PostMapping("addPayment")
	public ResponseEntity<Map<String, Object>> addPayment(@RequestBody Map<String, Object> requestData) {
		String userId = (String) requestData.get("userId");
		String lectureName = (String) requestData.get("lectureTitle");
        int lecturePrice = (int) requestData.get("lecturePrice");
        int lectureId = (int) requestData.get("lectureId");
        
        
        System.out.println("Received data:");
        System.out.println("userId: " + userId);
        System.out.println("lectureTitle: " + lectureName);
        System.out.println("lecturePrice: " + lecturePrice);
        System.out.println("lectureId: " + lectureId);
        
        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data received successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
	}
}