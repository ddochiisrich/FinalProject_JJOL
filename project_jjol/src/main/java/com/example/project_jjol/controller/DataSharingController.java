package com.example.project_jjol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.project_jjol.model.DataSharingComment;
import com.example.project_jjol.model.User;
import com.example.project_jjol.service.DataSharingCommentService;
import com.example.project_jjol.service.DataSharingService;
import com.example.project_jjol.service.LectureService;
import com.example.project_jjol.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

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
    public String saveData(DataSharing datasharing, @RequestParam("fileData") MultipartFile file,
                           @RequestParam("dateData") Date dateData, RedirectAttributes redirectAttributes) throws Exception {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "파일을 선택해 주세요.");
            return "redirect:/InstructorWrite";
        }

        // 파일 저장
        String fileName = datasharingService.storeFile(file);
        datasharing.setDataFile(fileName);

        Timestamp myDate = new Timestamp(dateData.getTime());
        datasharing.setDataDate(myDate);

        datasharingService.InstructorWrite(datasharing);
        return "redirect:/DataSharingView";
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
        model.addAttribute("datasharingList", datasharingList);
        return "views/DataSharing_View";
    }

    // 글 상세보기
    @GetMapping("/DataSharingViewDetail")
    public String dataSharingViewDetail(@RequestParam("no") int no, Model model) {
        DataSharing datasharing = datasharingService.findByNo(no);
        if (datasharing == null) {
            return "redirect:/DataSharingView";
        }
        List<DataSharingComment> commentList = datasharingcommentSerivce.getCommentsByDataNo(no);
        log.info("commentList : " + commentList);

        model.addAttribute("dsccommentList", commentList);
        model.addAttribute("datasharing", datasharing);
        return "views/DataSharing_Detail";
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
        return "redirect:/DataSharingView";
    }

    // 글 수정 폼 이동
    @GetMapping("/updateDataSharingForm")
    public String updateDataSharingForm(@RequestParam("no") int no, Model model) {
        DataSharing datasharing = datasharingService.findByNo(no);
        model.addAttribute("datasharing", datasharing);
        return "views/DataSharing_Update";
    }

    // 검색
    @GetMapping("/DataSharingSearch")
    public String searchDataSharing(@RequestParam("search_query") String searchQuery, Model model) {
        List<DataSharing> searchResults = datasharingService.searchDataSharing(searchQuery);
        model.addAttribute("datasharingList", searchResults);
        return "views/DataSharing_View";
    }

    // 파일 다운로드
    @GetMapping("/downloadFile/{fileName:.+}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        try {
            String fileUrl = datasharingService.getFileUrl(fileName);
            response.sendRedirect(fileUrl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}