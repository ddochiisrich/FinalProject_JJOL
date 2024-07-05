package com.example.project_jjol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.repository.PaymentMapper;

@Service
public class PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    public boolean validatePayment(Integer lectureId, Integer totalAmount) {
        Integer price = paymentMapper.getPriceByLectureId(lectureId);
        return price != null && price.equals(totalAmount);
    }
}