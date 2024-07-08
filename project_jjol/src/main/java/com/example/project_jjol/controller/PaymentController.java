package com.example.project_jjol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.service.PaymentService;

@RestController
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/addPayment")
    @ResponseBody
    public void addPayment(Integer price, String userId, Integer lectureId) {
    	
    }
}