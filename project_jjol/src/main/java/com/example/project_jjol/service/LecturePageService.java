package com.example.project_jjol.service;

import com.example.project_jjol.model.LecturePage;
import com.example.project_jjol.repository.LecturePageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturePageService {

    @Autowired
    private LecturePageMapper lecturePageMapper;

    public LecturePage getLecturePage(int lectureId, String userId, int chapterId) {
        try {
            return lecturePageMapper.findLecturePage(lectureId, userId, chapterId);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            throw new RuntimeException("Failed to get lecture page", e);
        }
    }

    public void saveOrUpdateLecturePage(LecturePage lecturePage) {
        try {
            if (lecturePageMapper.findLecturePage(lecturePage.getLectureId(), lecturePage.getUserId(), lecturePage.getChapterId()) != null) {
                lecturePageMapper.updateLecturePage(lecturePage);
            } else {
                lecturePageMapper.saveLecturePage(lecturePage);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            throw new RuntimeException("Failed to save or update lecture page", e);
        }
    }
}
