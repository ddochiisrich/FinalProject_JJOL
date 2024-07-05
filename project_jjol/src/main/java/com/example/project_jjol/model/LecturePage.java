package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturePage {
    private int pageId;
    private int lectureId;
    private String userId;
    private int chapterId;
    private int startTime;
    private Timestamp lastViewed;
}
