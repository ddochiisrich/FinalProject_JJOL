package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureNote {
    private int noteId;
    private int lectureId;
    private String userId;
    private String noteTitle;
    private String noteContent;
    private Timestamp createdAt;
}
