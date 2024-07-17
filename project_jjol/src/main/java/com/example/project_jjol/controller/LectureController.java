package com.example.project_jjol.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.LectureAnswer;
import com.example.project_jjol.model.LectureQuestion;
import com.example.project_jjol.model.LectureReview;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.LectureAnswerService;
import com.example.project_jjol.service.LectureApplicationService;
import com.example.project_jjol.service.LectureQuestionService;
import com.example.project_jjol.service.LectureReviewService;
import com.example.project_jjol.service.LectureService;
import com.example.project_jjol.service.NotificationService;
import com.example.project_jjol.service.S3Service;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureApplicationService lectureApplicationService;

    @Autowired
    private LectureReviewService lectureReviewService;

    @Autowired
    private LectureQuestionService lectureQuestionService;
    
    @Autowired
    private S3Service s3Service; // S3Service 주입

    @Autowired
    private LectureAnswerService lectureAnswerService;
    
    @Autowired
    private final NotificationService notificationService;
    
    @Autowired
    private UserService userService;

    @GetMapping({"/", "/lectures"})
    public String lectureList(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            User updatedUser = userService.findById(loggedInUser.getUserId());
            session.setAttribute("loggedInUser", updatedUser);
            String userId = loggedInUser.getUserId();
            model.addAttribute("mostUrgentNotification", notificationService.getMostUrgentNotification(userId));
        }

        Map<String, List<Lecture>> categorizedLectures = new HashMap<>();
        String[] keywords = {"spring", "react", "script"};

        for (String keyword : keywords) {
            categorizedLectures.put(keyword, lectureService.getLecturesByKeyword(keyword, 5));
        }

        model.addAttribute("categorizedLectures", categorizedLectures);
        model.addAttribute("latestLectures", lectureService.getAllLectures());

        return "views/lecture_list";  
    }



    @GetMapping("/lectures/search")
    public String searchLectures(@RequestParam("keyword") String keyword, Model model) {
        List<Lecture> lectures = lectureService.searchLectures(keyword);
        model.addAttribute("lectures", lectures);
        return "views/lecture_list";
    }
    
    @PostMapping("/lectures/review")
    public String addReview(@RequestParam("lectureId") int lectureId,
                            @RequestParam("rating") double rating,
                            @RequestParam("reviewContent") String reviewContent,
                            HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        LectureReview review = new LectureReview();
        review.setLectureId(lectureId);
        review.setUserId(user.getUserId());
        review.setRating(rating);
        review.setReviewContent(reviewContent);

        lectureReviewService.addReview(review);

        return "redirect:/lectures/detail/" + lectureId;
    }

    @GetMapping("/lectures/detail/{id}")
    public String lectureDetail(@PathVariable("id") int id, HttpSession session, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        boolean hasApplied = lectureApplicationService.hasUserAppliedForLecture(user.getUserId(), id);
        List<LectureReview> reviews = lectureReviewService.getReviewsByLectureId(id);
        double averageRating = lectureService.getAverageRating(id);

        // 소수점 1자리로 포맷팅
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedAverageRating = df.format(averageRating);

        long numberOfStudents = lectureService.getNumberOfStudents(id);
        List<LectureQuestion> questions = lectureQuestionService.getQuestionsByLectureId(id);

        for (LectureQuestion question : questions) {
            List<LectureAnswer> answers = lectureAnswerService.getAnswersByQuestionId(question.getId());
            question.setAnswers(answers);
        }

        model.addAttribute("lecture", lecture);
        model.addAttribute("hasApplied", hasApplied);
        model.addAttribute("loggedInUser", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", formattedAverageRating); // 포맷된 평점 추가
        model.addAttribute("numberOfStudents", numberOfStudents);
        model.addAttribute("questions", questions);
        return "views/lectureDetail";
    }


    @PostMapping("/lectures/apply")
    public String applyForLecture(@RequestParam("lectureId") int lectureId, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        lectureApplicationService.applyForLecture(user.getUserId(), lectureId);

        return "redirect:/lectures/detail/" + lectureId;
    }

    @GetMapping("/lectures/apply/{id}")
    public String showApplyPage(@PathVariable("id") int id, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        model.addAttribute("lecture", lecture);
        return "views/lectureApply";
    }

    @PostMapping("/lectures/questions")
    public String addQuestion(@RequestParam("lectureId") int lectureId,
                              @RequestParam("content") String content,
                              HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        boolean hasApplied = lectureApplicationService.hasUserAppliedForLecture(user.getUserId(), lectureId);

        if (!hasApplied) {
            return "redirect:/lectures/detail/" + lectureId;  // 해당 강의를 수강한 사용자만 질문할 수 있도록 설정
        }

        LectureQuestion question = new LectureQuestion();
        question.setLectureId(lectureId);
        question.setUserId(user.getUserId());
        question.setContent(content);

        lectureQuestionService.addQuestion(question);

        return "redirect:/lectures/detail/" + lectureId;
    }


    @PostMapping("/lectures/answers")
    public String addAnswer(@RequestParam("questionId") int questionId,
                            @RequestParam("content") String content,
                            HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        LectureQuestion question = lectureQuestionService.getQuestionById(questionId);
        Lecture lecture = lectureService.getLectureById(question.getLectureId());

        if (!lecture.getInstructorId().equals(user.getUserId())) {
            return "redirect:/lectures/detail/" + question.getLectureId();
        }

        LectureAnswer answer = new LectureAnswer();
        answer.setQuestionId(questionId);
        answer.setInstructorId(user.getUserId());
        answer.setContent(content);

        lectureAnswerService.addAnswer(answer);

        return "redirect:/lectures/detail/" + question.getLectureId();
    }

    @GetMapping("/videos/player")
    public String videoPlayer(@RequestParam("lectureId") int lectureId, HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        User user = (User) loggedInUser;
        List<Chapter> chapters = lectureService.getChaptersByLectureId(lectureId);
        Lecture lecture = lectureService.getLectureById(lectureId);
        Chapter lastViewedChapter = lectureService.getLastViewedChapter(lectureId, user.getUserId());

        if (!chapters.isEmpty()) {
            model.addAttribute("firstChapterUrl", lastViewedChapter != null ? lastViewedChapter.getChapterUrl() : chapters.get(0).getChapterUrl());
        } else {
            model.addAttribute("firstChapterUrl", lecture.getLectureThumbnailVideo());
        }

        model.addAttribute("chapters", chapters);
        model.addAttribute("lecture", lecture);
        return "views/videoPlayer"; // JSON 응답을 보내지 않고 HTML 템플릿을 렌더링
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
        return "views/upload";
    }
    
    @PostMapping("/lecturePage/reset/{lectureId}")
    public ResponseEntity<Map<String, String>> resetLecturePages(@PathVariable int lectureId, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = (User) loggedInUser;
        lectureService.resetLecturePages(lectureId, user.getUserId());

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    

}
