package com.example.project_jjol.controller;

import com.example.project_jjol.model.Certificate;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.UserService;
import com.example.project_jjol.service.LectureService;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MypageController {

    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        // 세션 업데이트
        User updatedUser = userService.findById(loggedInUser.getUserId());
        session.setAttribute("loggedInUser", updatedUser);

        List<Lecture> lectures = userService.getLecturesByUserId(loggedInUser.getUserId());
        List<Boolean> certificatesIssued = lectures.stream()
            .map(lecture -> userService.hasIssuedCertificate(loggedInUser.getUserId(), lecture.getLectureId()))
            .collect(Collectors.toList());

        List<Boolean> allChaptersCompleted = lectures.stream()
            .map(lecture -> userService.hasCompletedAllChapters(loggedInUser.getUserId(), lecture.getLectureId()))
            .collect(Collectors.toList());

        model.addAttribute("lectures", lectures);
        model.addAttribute("certificatesIssued", certificatesIssued);
        model.addAttribute("allChaptersCompleted", allChaptersCompleted);
        model.addAttribute("user", updatedUser);
        return "views/mypage";
    }

    @GetMapping("/mypage/issueCertificate/{lectureId}")
    public String issueCertificate(@PathVariable("lectureId") int lectureId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        try {
            userService.issueCertificate(loggedInUser.getUserId(), lectureId);
            model.addAttribute("successMessage", "수료증이 발급되었습니다.");
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "수료증 발급 중 오류가 발생했습니다.");
        }

        return mypage(session, model);  // mypage 메서드를 호출하여 모델을 다시 설정
    }

    @GetMapping("/mypage/certificate/{lectureId}")
    public String viewCertificate(@PathVariable("lectureId") int lectureId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Certificate certificate = userService.getCertificate(loggedInUser.getUserId(), lectureId);
        Lecture lecture = lectureService.getLectureById(lectureId);

        model.addAttribute("certificate", certificate);
        model.addAttribute("lecture", lecture);
        model.addAttribute("user", loggedInUser);
        return "views/certificate";
    }

    @GetMapping("/mypage/editProfile")
    public String showEditProfilePage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        return "views/editProfile";
    }

    @PostMapping("/mypage/editProfile")
    public String editProfile(@RequestParam("name") String name, 
                              @RequestParam("email") String email, 
                              @RequestParam("phone") String phone, 
                              HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        User user = userService.findById(loggedInUser.getUserId());
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        userService.updateUser(user);

        session.setAttribute("loggedInUser", user); // 세션 업데이트

        return "redirect:/mypage";
    }
}
