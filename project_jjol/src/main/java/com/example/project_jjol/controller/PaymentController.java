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
		
		// ajax 데이터 추출
        int lectureId = Integer.parseInt(requestData.get("lectureId").toString());
        String userId = (String) requestData.get("userId");
        String lectureTitle = (String) requestData.get("lectureTitle");
        int finalPriceFromView = Integer.parseInt(requestData.get("lecturePrice").toString());
        int point = Integer.parseInt(requestData.get("point").toString());
        int usedPoint = Integer.parseInt(requestData.get("usingPoint").toString());
        
        // DB 기록(Payment table)
        Payment payment = new Payment();
        payment.setLectureId(lectureId);
        payment.setPrice(finalPriceFromView);
        payment.setUserId(userId);
        payment.setLectureTitle(lectureTitle);
        
        paymentService.savePayment(payment);
        
        
        // DB 기록(User table에 포인트 반영)
        User user = new User();
        user.setUserId(userId);
        user.setPoint(point);
        
        int pointBeforePayment = paymentService.getUserPoint(usedPoint); // 결제검증 위함
        paymentService.updatePoint(user);
        int currentPoint = paymentService.getUserPoint(usedPoint); // 결제검증 위함
        
        // 결제 검증
        int discountedPriceFromDB = paymentService.getDiscountedPrice(lectureId);
        
        if((finalPriceFromView != (discountedPriceFromDB - usedPoint))
        		&& ((pointBeforePayment - usedPoint) != currentPoint)) {
        	// 결제 취소
        }
        
        // 응답 데이터
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data received successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
	}
}