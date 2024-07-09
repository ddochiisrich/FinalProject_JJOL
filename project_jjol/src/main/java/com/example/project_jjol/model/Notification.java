package com.example.project_jjol.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private String userName; // 사용자 id
    private String subject;
    private LocalDate examDate;
    private int daysUntilExam;

}
