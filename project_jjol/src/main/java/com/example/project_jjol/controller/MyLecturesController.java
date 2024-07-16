package com.example.project_jjol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.MyLecturesService;
import com.example.project_jjol.service.S3Service;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyLecturesController {
	
	@Autowired
	private MyLecturesService myLecturesService;
	
    @Autowired
    private S3Service s3Service;
	
	@GetMapping("/myLectures")
	public String myLectureList(HttpSession session, Model model) throws IOException {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			return "redirect:/login";
		}
		
		if("student".equals(loggedInUser.getRole())) {
			model.addAttribute("errorMessage", "강사 전용 페이지입니다.");
			return "views/myLectures";
		}
		
		List<Lecture> lectures = myLecturesService.findMyLecturesByUserId(loggedInUser.getUserId());
		
		model.addAttribute("lectures", lectures);
		model.addAttribute("user", loggedInUser);
		
		return "views/myLectures";
	}
	
	@PostMapping("/deleteLecture")
	public String deleteLecture(HttpSession session, @RequestParam("lectureId") int lectureId,
			@RequestParam("password") String password, Model model, RedirectAttributes redirectAttributes) throws IOException {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (password.equals(loggedInUser.getPass())) {
			try {
				myLecturesService.deleteLecture(lectureId);
				redirectAttributes.addFlashAttribute("successMessage", "강의가 성공적으로 삭제되었습니다.");
			} catch(Exception e) {
				redirectAttributes.addFlashAttribute("errorMessage", "수강생이 있는 강의는 삭제할 수 없습니다. 문의: 고객센터(1544-0000)");
				return "redirect:/myLectures";
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "redirect:/myLectures";
		}
		return "redirect:/myLectures";
	}
	
	@PostMapping("updateLecture")
	public String updateLecture(HttpSession session, Model model,
			@RequestParam("lectureId") int lectureId,
			@RequestParam("password") String password) throws IOException {
		
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (password.equals(loggedInUser.getPass())) {
			Lecture lecture = myLecturesService.findLectureByLectureId(lectureId);
			List<Chapter> chapters = myLecturesService.findChaptersByLectureId(lectureId);
			
			model.addAttribute("lecture", lecture);
			model.addAttribute("loggedInUser", loggedInUser);
			model.addAttribute("chapters", chapters);
			
		} else {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "views/myLectures";
		}
		
		return "views/lectureUpdateForm";
	}
	
	@PostMapping("updateProcess")
	public String updateLectureProcess(@ModelAttribute Lecture lecture,
			@RequestParam("thumbnailVideo") MultipartFile thumbnailVideo,
            @RequestParam("thumbnailImage") MultipartFile thumbnailImage,
            @RequestParam("chapterIds") List<Integer> chapterIds,
            @RequestParam("chapterTitles") List<String> chapterTitles,
            @RequestParam("chapterDescriptions") List<String> chapterDescriptions,
            @RequestParam("chapterFiles") List<MultipartFile> chapterFiles,
            @RequestParam("chapterOrders") List<Integer> chapterOrders) throws IOException{
		
		if (thumbnailVideo != null && !thumbnailVideo.isEmpty()) {
			lecture.setLectureThumbnailVideo(s3Service.uploadFile(thumbnailVideo)); // S3Service 사용
		}
		if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
			lecture.setLectureThumbnailImage(s3Service.uploadFile(thumbnailImage)); // S3Service 사용
		}
		
		List<Chapter> chapters = new ArrayList<>();
	    for (int i = 0; i < chapterIds.size(); i++) {
	        Chapter chapter = new Chapter();
	        chapter.setChapterId(chapterIds.get(i));
	        chapter.setChapterTitle(chapterTitles.get(i));
	        chapter.setChapterDescription(chapterDescriptions.get(i));
	        if (chapterFiles.get(i) != null && !chapterFiles.get(i).isEmpty()) {
	            chapter.setChapterUrl(s3Service.uploadFile(chapterFiles.get(i)));
	        }
	        chapter.setChapterOrder(chapterOrders.get(i));
	        chapters.add(chapter);
	    }
        
		myLecturesService.updateLecture(lecture);
		myLecturesService.updateChapter(chapters);
		return "redirect:myLectures";
	}
}
