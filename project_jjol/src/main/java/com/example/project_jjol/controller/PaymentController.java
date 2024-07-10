package com.example.project_jjol.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.PaymentService;

@RestController
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("addPayment")
	public ResponseEntity<Map<String, Object>> addPayment(@RequestBody Map<String, Object> requestData) {
		
		// DB 기록
		String userId = (String) requestData.get("userId");
		// Payment table
		String lectureTitle = (String) requestData.get("lectureTitle");
        int price = Integer.parseInt(requestData.get("lecturePrice").toString());
        int lectureId = Integer.parseInt(requestData.get("lectureId").toString());
        
        Payment payment = new Payment();
        payment.setLectureId(lectureId);
        payment.setPrice(price);
        payment.setUserId(userId);
        payment.setLectureTitle(lectureTitle);
        
        paymentService.savePayment(payment);
        
        // User table(포인트 반영)
        int point = Integer.parseInt(requestData.get("point").toString());
        
        User user = new User();
        user.setUserId(userId);
        user.setPoint(point);
        
        paymentService.updatePoint(user);
        
        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data received successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
	}
}