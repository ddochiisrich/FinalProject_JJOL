package com.example.project_jjol.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.model.LecturePage;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.LecturePageService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/lecturePage")
public class LecturePageController {

    @Autowired
    private LecturePageService lecturePageService;

    @GetMapping("/{lectureId}/{chapterId}")
    public ResponseEntity<Map<String, Object>> getLecturePage(
            @PathVariable("lectureId") int lectureId,
            @PathVariable("chapterId") int chapterId,
            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Handle not logged in case
        }
        try {
            LecturePage lecturePage = lecturePageService.getLecturePage(lectureId, loggedInUser.getUserId(), chapterId);
            Map<String, Object> response = new HashMap<>();
            response.put("lecturePage", lecturePage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveOrUpdateLecturePage(
            @RequestBody LecturePage lecturePage, 
            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Handle not logged in case
        }
        lecturePage.setUserId(loggedInUser.getUserId());
        lecturePageService.saveOrUpdateLecturePage(lecturePage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{lectureId}/lastViewed")
    public ResponseEntity<Map<String, Object>> getLastViewedChapter(
            @PathVariable("lectureId") int lectureId,
            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Map<String, Object> lastViewedPage = lecturePageService.getLastViewedChapter(lectureId, loggedInUser.getUserId());
            if (lastViewedPage == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(lastViewedPage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
