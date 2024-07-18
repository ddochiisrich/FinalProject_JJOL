package com.example.project_jjol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.PaymentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentListController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/paymentList")
	public String paymentList(HttpSession session, Model model) {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if(! ("admin".equals(loggedInUser.getRole()))) {
			model.addAttribute("errorMessage", "관리자 전용 페이지입니다.");
			return "redirect:/lectures";
		}
		
		List<Payment> paymentList = paymentService.paymentList();
		
		model.addAttribute("paymentList", paymentList);
		
		return "views/paymentList";
	}
}
