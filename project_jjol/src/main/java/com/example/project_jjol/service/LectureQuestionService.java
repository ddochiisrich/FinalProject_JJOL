package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.LectureQuestion;
import com.example.project_jjol.repository.LectureQuestionMapper;

@Service
public class LectureQuestionService {

    @Autowired
    private LectureQuestionMapper lectureQuestionMapper;

    @Transactional(readOnly = true)
    public List<LectureQuestion> getQuestionsByLectureId(int lectureId) {
        return lectureQuestionMapper.findByLectureId(lectureId);
    }

    @Transactional
    public void addQuestion(LectureQuestion question) {
        lectureQuestionMapper.insertQuestion(question);
    }

    @Transactional(readOnly = true)
    public LectureQuestion getQuestionById(int questionId) {
        return lectureQuestionMapper.findById(questionId);
    }
}
