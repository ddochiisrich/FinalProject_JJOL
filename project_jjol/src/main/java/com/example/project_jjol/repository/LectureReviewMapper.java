package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.LectureReview;

@Mapper
public interface LectureReviewMapper {

    @Insert("INSERT INTO lectureReview (review_content, lecture_id, user_id, rating, review_date) VALUES (#{reviewContent}, #{lectureId}, #{userId}, #{rating}, NOW())")
    void save(LectureReview review);

    @Select("SELECT * FROM lectureReview WHERE lecture_id = #{lectureId}")
    List<LectureReview> findByLectureId(int lectureId);
}
