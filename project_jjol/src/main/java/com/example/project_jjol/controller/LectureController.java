package com.example.project_jjol.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.service.LectureService;
import com.example.project_jjol.service.S3Service;

import jakarta.servlet.http.HttpSession;

@Controller
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private S3Service s3Service; // S3Service 주입

    @GetMapping("/lectures")
    public String lectureList(Model model) {
        model.addAttribute("lectures", lectureService.getAllLectures());
        return "lectures";
    }

    @GetMapping("/lectures/detail/{id}")
    public String lectureDetail(@PathVariable("id") int id, HttpSession session, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("lecture", lecture);
        model.addAttribute("loggedInUser", loggedInUser);
        return "lectureDetail";
    }

    @GetMapping("/videos/player")
    public String videoPlayer(@RequestParam("lectureId") int lectureId, Model model) {
        List<Chapter> chapters = lectureService.getChaptersByLectureId(lectureId);
        Lecture lecture = lectureService.getLectureById(lectureId);
        
        if (!chapters.isEmpty()) {
            model.addAttribute("firstChapterUrl", chapters.get(0).getChapterUrl());
        } else {
            model.addAttribute("firstChapterUrl", lecture.getLectureThumbnailVideo());
        }

        model.addAttribute("chapters", chapters);
        model.addAttribute("lecture", lecture);
        return "videoPlayer";
    }

    @PostMapping("/lectures/upload")
    public String uploadLecture(@RequestParam("title") String title,
                                @RequestParam("shortDescription") String shortDescription,
                                @RequestParam("longDescription") String longDescription,
                                @RequestParam("thumbnailVideo") MultipartFile thumbnailVideo,
                                @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
                                @RequestParam("level") String level,
                                @RequestParam("price") double price,
                                @RequestParam("discount") double discount,
                                @RequestParam("instructorId") String instructorId,
                                @RequestParam("instructorName") String instructorName,
                                @RequestParam("chapterTitles") List<String> chapterTitles,
                                @RequestParam("chapterDescriptions") List<String> chapterDescriptions,
                                @RequestParam("chapterFiles") List<MultipartFile> chapterFiles,
                                @RequestParam("chapterOrders") List<Integer> chapterOrders) throws IOException {
        
        // 강의 정보 설정
        Lecture lecture = new Lecture();
        lecture.setLectureTitle(title);
        lecture.setLectureShortDescription(shortDescription);
        lecture.setLectureLongDescription(longDescription);
        lecture.setLectureThumbnailVideo(s3Service.uploadFile(thumbnailVideo)); // S3Service 사용
        lecture.setLectureThumbnailImage(s3Service.uploadFile(thumbnailImage)); // S3Service 사용
        lecture.setLectureLevel(level);
        lecture.setLecturePrice(price);
        lecture.setLectureDiscount(discount);
        lecture.setInstructorId(instructorId);
        lecture.setInstructorName(instructorName);

        // 강의와 챕터 저장
        lectureService.saveLectureAndChapters(lecture, chapterFiles, chapterTitles, chapterDescriptions, chapterOrders);

        return "redirect:/lectures";
    }

    @GetMapping("/lectures/upload")
    public String showUploadForm(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        return "upload";
    }
}
