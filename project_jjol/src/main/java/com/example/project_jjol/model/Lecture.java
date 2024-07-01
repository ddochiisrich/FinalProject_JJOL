package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    private int lectureId;
    private String lectureTitle;
    private String lectureShortDescription;
    private String lectureLongDescription;
    private String lectureThumbnailVideo;
    private String lectureThumbnailImage;
    private String lectureLevel;
    private double lecturePrice;
    private double lectureDiscount;
    private int lectureLike;
    private String instructorId;
    private String instructorName; // 추가된 필드
}
