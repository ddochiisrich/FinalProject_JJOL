package com.example.project_jjol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_jjol.model.DataSharingComment;
import com.example.project_jjol.service.DataSharingCommentService;

@Controller
public class DataSharingCommentController {

    @Autowired
    private DataSharingCommentService datasharingcommentService;
    
 // 댓글 추가
    @PostMapping("/comments/datacommentadd")
    @ResponseBody
    public DataSharingComment datacommentadd(@RequestBody DataSharingComment datasharingcomment) {
        return datasharingcommentService.insertdatacomment(datasharingcomment);
    }

    
    // 특정 데이터 번호에 해당하는 모든 댓글 가져오기
    @GetMapping("/comments/byDataNo/{no}")
    @ResponseBody
    public List<DataSharingComment> getCommentsByDataNo(@PathVariable int no){
        return datasharingcommentService.getCommentsByDataNo(no);
    }
}
