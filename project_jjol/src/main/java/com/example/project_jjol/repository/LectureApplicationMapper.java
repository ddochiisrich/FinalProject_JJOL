package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.LectureApplication;

@Mapper
public interface LectureApplicationMapper {

    @Select("SELECT * FROM LectureApplication WHERE user_id = #{userId} AND lecture_id = #{lectureId}")
    List<LectureApplication> findByUserIdAndLectureId(@Param("userId") String userId, @Param("lectureId") int lectureId);

    @Insert("INSERT INTO LectureApplication (user_id, lecture_id, application_date) VALUES (#{userId}, #{lectureId}, #{applicationDate})")
    void insertLectureApplication(LectureApplication lectureApplication);

    @Select("SELECT COUNT(*) FROM LectureApplication WHERE lecture_id = #{lectureId}")
    long countStudentsByLectureId(@Param("lectureId") int lectureId);

    @Select("SELECT l.* FROM LectureApplication la JOIN Lecture l ON la.lecture_id = l.lecture_id WHERE la.user_id = #{userId}")
    List<Lecture> findLecturesByUserId(@Param("userId") String userId);
}
