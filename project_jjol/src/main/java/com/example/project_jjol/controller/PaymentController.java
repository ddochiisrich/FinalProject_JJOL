package com.example.project_jjol.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	@PostMapping("validatePayment")
	public ResponseEntity<Map<String, Object>> validatePayment(@RequestBody Map<String, Object> requestData) {
		// ajax 데이터 추출
		int lectureId = Integer.parseInt(requestData.get("lectureId").toString());
		String userId = (String) requestData.get("userId");
		int finalPriceFromView = Integer.parseInt(requestData.get("lecturePrice").toString());
		int usingPoint = Integer.parseInt(requestData.get("usingPoint").toString());
		
		// DB 데이터 추출
        int discountedPriceFromDB = paymentService.getDiscountedPrice(lectureId);
        int pointFromDB = paymentService.getUserPoint(userId);
		
        // 검증
		if((finalPriceFromView != (discountedPriceFromDB - usingPoint))
				|| ((pointFromDB - usingPoint) < 0)) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "Payment validation failed");
			errorResponse.put("status", "failure");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		
		// 응답 데이터
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data received successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("addPayment")
	public ResponseEntity<Map<String, Object>> addPayment(@RequestBody Map<String, Object> requestData) {
		
		// ajax 데이터 추출
		int lectureId = Integer.parseInt(requestData.get("lectureId").toString());
		String userId = (String) requestData.get("userId");
		int lecturePrice = Integer.parseInt(requestData.get("lecturePrice").toString());
        String lectureTitle = (String) requestData.get("lectureTitle");
        String merchantUid = (String) requestData.get("merchantUid");
        int point = Integer.parseInt(requestData.get("point").toString());
        
        // DB 기록(Payment table)
        Payment payment = new Payment();
        payment.setLectureId(lectureId);
        payment.setPrice(lecturePrice);
        payment.setUserId(userId);
        payment.setLectureTitle(lectureTitle);
        payment.setMerchantUid(merchantUid);
        
        paymentService.savePayment(payment);
        
        // DB 기록(User table에서 포인트 업데이트)
        int finalPoint = point + lecturePrice * 5 / 100;
        User user = new User();
        user.setUserId(userId);
        user.setPoint(finalPoint);

        paymentService.updatePoint(user);
        
        // 응답 데이터
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data received successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
	}
}