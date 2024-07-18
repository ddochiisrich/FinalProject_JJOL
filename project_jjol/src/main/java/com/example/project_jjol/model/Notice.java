package com.example.project_jjol.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notice {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private String userId; // User의 userId를 참조
}
