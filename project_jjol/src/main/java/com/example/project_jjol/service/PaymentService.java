package com.example.project_jjol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.repository.PaymentMapper;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Transactional
	public void addPayment(Integer price, String userId, Integer lectureId) {
		paymentMapper.addPayment(lectureId, userId, lectureId);
	}
}
