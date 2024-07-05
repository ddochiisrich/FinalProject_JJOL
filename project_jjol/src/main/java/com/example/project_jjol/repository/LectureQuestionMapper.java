package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.LectureQuestion;

@Mapper
public interface LectureQuestionMapper {

    @Select("SELECT * FROM lectureQuestions WHERE lecture_id = #{lectureId}")
    List<LectureQuestion> findByLectureId(@Param("lectureId") int lectureId);

    @Insert("INSERT INTO lectureQuestions (lecture_id, user_id, content) VALUES (#{lectureId}, #{userId}, #{content})")
    void insertQuestion(LectureQuestion question);

    @Select("SELECT * FROM lectureQuestions WHERE id = #{id}")
    LectureQuestion findById(@Param("id") int id);
}
