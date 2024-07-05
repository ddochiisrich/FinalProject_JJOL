package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.LectureAnswer;

@Mapper
public interface LectureAnswerMapper {

    @Select("SELECT * FROM lectureAnswers WHERE question_id = #{questionId}")
    List<LectureAnswer> findByQuestionId(@Param("questionId") int questionId);

    @Insert("INSERT INTO lectureAnswers (question_id, instructor_id, content) VALUES (#{questionId}, #{instructorId}, #{content})")
    void insertAnswer(LectureAnswer answer);
}
