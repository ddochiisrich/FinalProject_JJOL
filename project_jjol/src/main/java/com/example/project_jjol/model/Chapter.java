package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private int chapterId;
    private int lectureId;
    private String chapterTitle;
    private String chapterDescription;
    private String chapterUrl;
    private int chapterOrder;
}
