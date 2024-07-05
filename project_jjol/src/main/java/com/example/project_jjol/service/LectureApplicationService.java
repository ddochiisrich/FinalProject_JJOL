package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.repository.LectureApplicationMapper;
import com.example.project_jjol.model.LectureApplication;

@Service
public class LectureApplicationService {

    @Autowired
    private LectureApplicationMapper lectureApplicationMapper;

    public boolean hasUserAppliedForLecture(String userId, int lectureId) {
        List<LectureApplication> applications = lectureApplicationMapper.findByUserIdAndLectureId(userId, lectureId);
        return !applications.isEmpty();
    }

    public void applyForLecture(String userId, int lectureId) {
        LectureApplication application = new LectureApplication();
        application.setUserId(userId);
        application.setLectureId(lectureId);
        lectureApplicationMapper.insertLectureApplication(application);
    }

    public long getNumberOfStudentsByLectureId(int lectureId) {
        return lectureApplicationMapper.countStudentsByLectureId(lectureId);
    }
}
