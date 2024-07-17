package com.example.project_jjol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_jjol.model.DataSharingComment;
import com.example.project_jjol.service.DataSharingCommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DataSharingCommentController {

    @Autowired
    private DataSharingCommentService datasharingcommentService;

    // 댓글 추가
    @PostMapping("/comments/datacommentadd")
    @ResponseBody
    public List<DataSharingComment> datacommentadd(@RequestBody DataSharingComment datasharingcomment) {

        log.info("dsComment : " + datasharingcomment.getDscContent() + ", writer : "
                + datasharingcomment.getDscWriter());

        datasharingcommentService.insertdatacomment(datasharingcomment);
        return datasharingcommentService.getCommentsByDataNo(datasharingcomment.getDdNo());
    }

    // 특정 데이터 번호에 해당하는 모든 댓글 가져오기
    @GetMapping("/comments/byDataNo/{no}")
    @ResponseBody
    public List<DataSharingComment> getCommentsByDataNo(@PathVariable("no") int no) {
        return datasharingcommentService.getCommentsByDataNo(no);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteComment(@PathVariable("id") int id, @RequestParam("currentUser") String currentUser) {
        try {
            DataSharingComment comment = datasharingcommentService.getCommentById(id);
            if (comment != null && comment.getDscWriter().equals(currentUser)) {
                datasharingcommentService.deleteComment(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Error deleting comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
