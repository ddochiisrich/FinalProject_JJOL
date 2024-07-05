package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private int certificateId;
    private String userId;
    private int lectureId;
    private String issueDate;
}
