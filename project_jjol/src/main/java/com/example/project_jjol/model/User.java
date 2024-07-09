package com.example.project_jjol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String pass;
    private String name;
    private String email;
    private String phone;
    private String role; // "student" 또는 "instructor" 역할 구분
    private int point;
    private Timestamp regDate;
    private String provider;
}
