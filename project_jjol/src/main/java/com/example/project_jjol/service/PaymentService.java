package com.example.project_jjol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.model.User;
import com.example.project_jjol.repository.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	
	public void savePayment(Payment payment) {
		paymentMapper.savePayment(payment);
	}
	
	public void updatePoint(User user) {
		paymentMapper.updatePoint(user);
	}
	
	public int getDiscountedPrice(int lectureId) {
		return paymentMapper.discountedPrice(lectureId);
	}
	
	public int getUserPoint(int userId) {
		return paymentMapper.userPoint(userId);
	}
}
