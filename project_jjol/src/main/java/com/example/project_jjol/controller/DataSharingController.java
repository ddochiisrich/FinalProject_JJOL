package com.example.project_jjol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project_jjol.model.DataSharing;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.DataSharingCommentService;
import com.example.project_jjol.service.DataSharingService;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import com.example.project_jjol.service.LectureService;

@Controller
@Slf4j
public class DataSharingController {

	@Autowired
	private DataSharingService datasharingService;
	
	@Autowired
	private DataSharingCommentService datasharingcommentSerivce;
	@Autowired
	private UserService userService;
	@Autowired
	private LectureService lectureService;

	// 글 쓰기 폼 요청(강사)
	@GetMapping("/InstructorWrite")
	public String addData(Model model, HttpSession session) {

		User user = (User) session.getAttribute("loggedInUser");

	       if (user != null) {
	            List<String> lectures = lectureService.getLecturesByInstructorId(user.getUserId());
	            log.info("lectures : " + lectures);
	            model.addAttribute("lectures", lectures);
	        }

		return "views/DataSharing_Instructor_Write";
	}

	// 글 쓰기 요청 처리 (강사)
	@PostMapping("/InstructorWrite")
	public String saveData(DataSharing datasharing, @RequestParam(value = "fileData") MultipartFile file,
			@RequestParam(value = "dateData") Date dateData, RedirectAttributes redirectAttributes) throws Exception {

		/*
		 * 파일을 꼭 업로드 해야 하는가? if (file.isEmpty()) {
		 * redirectAttributes.addFlashAttribute("errorMessage", "파일을 선택해 주세요."); return
		 * "redirect:/DataSharingView"; }
		 */
		Timestamp myDate = new Timestamp(dateData.getTime());
		log.info("time :  " + myDate.getTime());

		// 시간 설정
		datasharing.setDataDate(myDate);
		// 파일 저장 서비스 호출
		// String fileName = datasharingService.storeFile(file);
		// datasharing.setDataFile(fileName); // 필드명 수정
		log.info("InstructorWrite - controller : " + datasharing.getDataName());
		DataSharing savedData = datasharingService.InstructorWrite(datasharing);

		return "redirect:/DataSharingView"; // 데이터 저장 후 목록 페이지로 리다이렉트
	}

	// 글 리스트 보기
	@GetMapping("/DataSharing")
	public String dataSharing(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser != null) {
			List<DataSharing> dataSharingList = datasharingService.getDataSharing();
			model.addAttribute("datasharingList", dataSharingList);
			return "views/DataSharing_View";
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/DataSharingView")
	public String dataSharingView(Model model) {
		List<DataSharing> datasharingList = datasharingService.getDataSharing();
		// db에 데이터를 읽어와서
		model.addAttribute("datasharingList", datasharingList);
		return "views/DataSharing_View";
	}

	// 글 상세보기
	@GetMapping("/DataSharingViewDetail")
	public String dataSharingViewDetail(@RequestParam("no") int no, Model model) {
		DataSharing datasharing = datasharingService.findByNo(no); // data_no로 글 데이터 조회
		if (datasharing == null) {
			// 글이 존재하지 않는 경우 처리 (예: 글 목록 페이지로 리다이렉트)
			return "redirect:/DataSharingView";
		}
		model.addAttribute("datasharing", datasharing); // 모델에 데이터 공유
		return "views/DataSharing_Detail"; // 글 상세보기 페이지로 이동
	}

	// 글 삭제하기
	@PostMapping("/deleteDataSharing")
	public String deleteDataSharing(HttpServletResponse response, PrintWriter out, @RequestParam("dataNo") int no) {
		datasharingService.deleteDataSharing(no);
		return "redirect:/DataSharingView";
	}

	// 글 수정하기
	@PostMapping("/updateDataSharing")
	public String updateDataSharing(@ModelAttribute("datasharing") DataSharing datasharing) {
		datasharingService.updateDataSharing(datasharing);
		return "redirect:/DataSharingView"; // 수정 후 리다이렉트할 페이지
	}

	// 글 수정 폼 이동
	@GetMapping("/updateDataSharingForm")
	public String updateDataSharingForm(@RequestParam("no") int no, Model model) {
		DataSharing datasharing = datasharingService.findByNo(no); // 데이터베이스에서 글 조회
		model.addAttribute("datasharing", datasharing); // 모델에 데이터 전달
		return "views/DataSharing_Update"; // 수정 폼 템플릿으로 이동
	}

}
