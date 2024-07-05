package com.example.project_jjol.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.example.project_jjol.service.LectureService;

@Controller
public class DataSharingController {

    @Autowired
    private DataSharingService datasharingService;
    @Autowired
    private UserService userService;
    @Autowired
    private LectureService lecturesService;

    @GetMapping("/dataSharing")
    public String dataSharing(Model model) {
        List<DataSharing> dataSharingList = datasharingService.findAll();
        model.addAttribute("datasharingList", dataSharingList);
        return "dataSharing";
    }

    // 글 쓰기 폼 요청(강사)
    @GetMapping("/InstructorWrite")
    public String addData() {
        return "DataSharing_Instructor_Write";
    }

    // 글 쓰기 요청 처리 (강사)
    @PostMapping("/InstructorWrite")
    public String saveData(DataSharing datasharing, @RequestParam("data_file") MultipartFile file, RedirectAttributes redirectAttributes) {
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

    // 파일 다운로드
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = datasharingService.getFilePath(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/DataSharing")
    public String redirectToDataSharing(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            if (loggedInUser.getRole().equals("instructor")) {
                // 강사 로그인 시 강사용 자료실
                return "redirect:/DataSharing_Instructor";
            } else if (loggedInUser.getRole().equals("student")) {
                // 학생 로그인 시 학생용 자료실
                return "redirect:/DataSharing_Student";
            }
        }
        // 로그인이 되지 않은 경우 로그인 페이지로 리다이렉트 또는 다른 처리
        return "redirect:/login";
    }

    @GetMapping("/DataSharing_Instructor")
    public String instructorDataSharing(Model model) {
        List<DataSharing> dataSharingList = datasharingService.getDataSharing();
        model.addAttribute("datasharingList", dataSharingList);
        return "DataSharing_Instructor";
    }

    @GetMapping("/DataSharing_Student")
    public String studentDataSharing(Model model) {
        List<DataSharing> dataSharingList = datasharingService.getDataSharing();
        model.addAttribute("datasharingList", dataSharingList);
        return "DataSharing_Student";
    }
}
