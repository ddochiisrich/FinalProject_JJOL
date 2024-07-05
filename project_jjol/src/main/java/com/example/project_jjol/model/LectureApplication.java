package com.example.project_jjol.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LectureApplication {
    private int applicationId;
    private LocalDateTime applicationDate = LocalDateTime.now();
    private String userId;
    private int lectureId;
}
