package com.example.project_jjol.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_jjol.model.LectureNote;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.LectureNoteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/notes")
public class LectureNoteController {

    @Autowired
    private LectureNoteService lectureNoteService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addLectureNote(@RequestParam("lectureId") int lectureId,
                                                 @RequestParam("noteTitle") String noteTitle,
                                                 @RequestParam("noteContent") String noteContent,
                                                 HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인이 필요합니다.");
        }

        LectureNote lectureNote = new LectureNote();
        lectureNote.setLectureId(lectureId);
        lectureNote.setUserId(loggedInUser.getUserId());
        lectureNote.setNoteTitle(noteTitle); // 추가된 필드
        lectureNote.setNoteContent(noteContent);

        lectureNoteService.saveLectureNote(lectureNote);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/{lectureId}")
    @ResponseBody
    public List<LectureNote> getLectureNotes(@PathVariable("lectureId") int lectureId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return Collections.emptyList(); // 로그인하지 않은 경우 빈 배열 반환
        }

        return lectureNoteService.getLectureNotesByLectureIdAndUserId(lectureId, loggedInUser.getUserId());
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteLectureNote(@RequestParam("noteId") int noteId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인이 필요합니다.");
        }

        try {
            lectureNoteService.deleteLectureNoteById(noteId);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("노트 삭제 중 오류가 발생했습니다.");
        }
    }
}
