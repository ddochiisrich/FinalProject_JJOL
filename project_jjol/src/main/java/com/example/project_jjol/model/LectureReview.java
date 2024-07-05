package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureReview {
    private int reviewId;
    private String reviewContent;
    private int lectureId;
    private String userId;
    private double rating;
    private Timestamp reviewDate;
}
