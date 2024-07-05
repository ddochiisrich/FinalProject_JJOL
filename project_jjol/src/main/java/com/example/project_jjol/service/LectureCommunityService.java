package com.example.project_jjol.service;

import com.example.project_jjol.model.LectureCommunity;
import com.example.project_jjol.repository.LectureCommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureCommunityService {

    @Autowired
    private LectureCommunityMapper lectureCommunityMapper;

    public void saveLectureCommunity(LectureCommunity lectureCommunity) {
        lectureCommunityMapper.saveLectureCommunity(lectureCommunity);
    }

    public List<LectureCommunity> getLectureCommunitiesByLectureId(int lectureId) {
        return lectureCommunityMapper.findByLectureId(lectureId);
    }

    public void deleteLectureCommunityById(int postId) {
        lectureCommunityMapper.deleteLectureCommunityById(postId);
    }
}
