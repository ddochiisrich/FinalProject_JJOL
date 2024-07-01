package com.example.project_jjol.controller;

import com.example.project_jjol.model.LectureCommunity;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.LectureCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/community")
public class LectureCommunityController {

    @Autowired
    private LectureCommunityService lectureCommunityService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addLectureCommunity(@RequestParam("lectureId") int lectureId,
                                                      @RequestParam("postTitle") String postTitle,
                                                      @RequestParam("postContent") String postContent,
                                                      HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인이 필요합니다.");
        }

        LectureCommunity lectureCommunity = new LectureCommunity();
        lectureCommunity.setLectureId(lectureId);
        lectureCommunity.setUserId(loggedInUser.getUserId());
        lectureCommunity.setPostTitle(postTitle);
        lectureCommunity.setPostContent(postContent);

        lectureCommunityService.saveLectureCommunity(lectureCommunity);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/{lectureId}")
    @ResponseBody
    public List<LectureCommunity> getLectureCommunities(@PathVariable("lectureId") int lectureId) {
        return lectureCommunityService.getLectureCommunitiesByLectureId(lectureId);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteLectureCommunity(@RequestParam("postId") int postId) {
        try {
            lectureCommunityService.deleteLectureCommunityById(postId);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제 중 오류가 발생했습니다.");
        }
    }
}
