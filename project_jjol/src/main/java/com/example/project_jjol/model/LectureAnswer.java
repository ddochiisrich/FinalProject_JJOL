package com.example.project_jjol.model;

import lombok.Data;

@Data
public class LectureAnswer {
    private int id;
    private int questionId;
    private String instructorId;
    private String content;
}
