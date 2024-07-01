package com.example.project_jjol.controller;

import com.example.project_jjol.model.LecturePage;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.LecturePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/lecturePage")
public class LecturePageController {

    @Autowired
    private LecturePageService lecturePageService;

    @GetMapping("/{lectureId}/{chapterId}")
    public ResponseEntity<LecturePage> getLecturePage(
            @PathVariable("lectureId") int lectureId,
            @PathVariable("chapterId") int chapterId,
            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Handle not logged in case
        }
        LecturePage lecturePage = lecturePageService.getLecturePage(lectureId, loggedInUser.getUserId(), chapterId);
        return new ResponseEntity<>(lecturePage, HttpStatus.OK);
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
}
