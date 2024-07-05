package com.example.project_jjol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.service.PaymentService;

// hi ㅎㅇㅋㅋ

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/validate")
    public boolean validatePayment(@RequestParam Integer lectureId, @RequestParam Integer totalAmount) {
        return paymentService.validatePayment(lectureId, totalAmount);
    }
}