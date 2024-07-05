package com.example.project_jjol.model;

import java.util.List;

import lombok.Data;

@Data
public class LectureQuestion {
    private int id;
    private int lectureId;
    private String userId;
    private String content;
    private List<LectureAnswer> answers;
}
