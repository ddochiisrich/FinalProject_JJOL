package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureCommunity {
    private int postId;
    private int lectureId;
    private String userId;
    private String postTitle;
    private String postContent;
    private Timestamp createdAt;
}
