package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.LectureReview;
import com.example.project_jjol.repository.LectureReviewMapper;

@Service
public class LectureReviewService {

    @Autowired
    private LectureReviewMapper lectureReviewMapper;

    public void addReview(LectureReview review) {
        lectureReviewMapper.save(review);
    }

    public List<LectureReview> getReviewsByLectureId(int lectureId) {
        return lectureReviewMapper.findByLectureId(lectureId);
    }
}
