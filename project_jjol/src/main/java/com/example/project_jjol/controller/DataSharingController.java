package com.example.project_jjol.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project_jjol.model.DataSharing;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.DataSharingService;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.project_jjol.service.LectureService;

@Controller
public class DataSharingController {

	@Autowired
	private DataSharingService datasharingService;
	@Autowired
	private UserService userService;
	@Autowired
	private LectureService lecturesService;

//    @GetMapping("/dataSharing")
//    public String dataSharing(Model model) {
//        List<DataSharing> dataSharingList = datasharingService.findAll();
//        model.addAttribute("datasharingList", dataSharingList);
//        return "dataSharing";
//    }

	// 글 쓰기 폼 요청(강사)
	@GetMapping("/InstructorWrite")
	public String addData() {
		return "DataSharing_Instructor_Write";
	}

	// 글 쓰기 요청 처리 (강사)
	@PostMapping("/InstructorWrite")
	public String saveData(DataSharing datasharing, @RequestParam("data_file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "파일을 선택해 주세요.");
			return "redirect:/DataSharing_Instructor";
		}

		try {
			// 파일 저장 서비스 호출
			String fileName = datasharingService.storeFile(file);
			datasharing.setDataFile(fileName); // 필드명 수정
			DataSharing savedData = datasharingService.InstructorWrite(datasharing);

			if (savedData != null) {
				redirectAttributes.addFlashAttribute("successMessage", "글이 성공적으로 등록되었습니다.");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "글 등록에 실패하였습니다. 다시 시도해 주세요.");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드에 실패하였습니다. 다시 시도해 주세요.");
		}

		return "redirect:/DataSharing_Instructor"; // 데이터 저장 후 목록 페이지로 리다이렉트
	}

	//글 리스트보기
	@GetMapping("/DataSharing")
	public String redirectToDataSharing(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		
		if (loggedInUser != null) {
			if (loggedInUser.getRole().equals("instructor") || loggedInUser.getRole().equals("student")) {
				return "redirect:/DataSharingView"; // 강사와 학생 모두 DataSharing_View로 리다이렉트
			}
		}

		// 로그인이 되지 않은 경우 로그인 페이지로 리다이렉트 또는 다른 처리
		return "redirect:/login";
	}
	
	@GetMapping("/DataSharingView")
	public String DataSharingView() {
		return "DataSharing_View";
	}



	

}
