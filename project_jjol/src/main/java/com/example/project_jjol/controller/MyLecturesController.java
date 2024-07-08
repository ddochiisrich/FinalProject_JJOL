package com.example.project_jjol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.MyLecturesService;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyLecturesController {
	
	@Autowired
	private MyLecturesService myLectureService;
			
	@GetMapping("/myLectures")
	public String myLectureList(HttpSession session, Model model, HttpServletResponse response) throws IOException {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		if("student".equals(loggedInUser.getRole())) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('강사용 페이지입니다.');");
			out.println(" location.href='lectures'");
			out.println("</script>");
			return null;
		}
		
		List<Lecture> lectures = myLectureService.getMyLecturesByUserId(loggedInUser.getUserId());
		
		model.addAttribute("lectures", lectures);
		model.addAttribute("user", loggedInUser);
		
		return "myLectures";
	}
}
