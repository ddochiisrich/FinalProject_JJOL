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
        return lecturePageMapper.findLecturePage(lectureId, userId, chapterId);
    }

    public void saveOrUpdateLecturePage(LecturePage lecturePage) {
        if (lecturePageMapper.findLecturePage(lecturePage.getLectureId(), lecturePage.getUserId(), lecturePage.getChapterId()) != null) {
            lecturePageMapper.updateLecturePage(lecturePage);
        } else {
            lecturePageMapper.saveLecturePage(lecturePage);
        }
    }
}
