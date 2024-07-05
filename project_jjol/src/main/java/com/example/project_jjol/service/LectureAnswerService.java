package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.LectureAnswer;
import com.example.project_jjol.repository.LectureAnswerMapper;

@Service
public class LectureAnswerService {

    @Autowired
    private LectureAnswerMapper lectureAnswerMapper;

    @Transactional(readOnly = true)
    public List<LectureAnswer> getAnswersByQuestionId(int questionId) {
        return lectureAnswerMapper.findByQuestionId(questionId);
    }

    @Transactional
    public void addAnswer(LectureAnswer answer) {
        lectureAnswerMapper.insertAnswer(answer);
    }
}
