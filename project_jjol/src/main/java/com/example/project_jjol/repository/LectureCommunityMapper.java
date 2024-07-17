package com.example.project_jjol.repository;

import com.example.project_jjol.model.LectureCommunity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LectureCommunityMapper {

    @Insert("INSERT INTO lectureCommunity (lecture_id, user_id, post_title, post_content, created_at) " +
            "VALUES (#{lectureId}, #{userId}, #{postTitle}, #{postContent}, NOW())")
    void saveLectureCommunity(LectureCommunity lectureCommunity);

    @Select("SELECT * FROM lectureCommunity WHERE lecture_id = #{lectureId}")
    List<LectureCommunity> findByLectureId(@Param("lectureId") int lectureId);

    @Delete("DELETE FROM lectureCommunity WHERE post_id = #{postId}")
    void deleteLectureCommunityById(@Param("postId") int postId);
}
