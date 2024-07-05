package com.example.project_jjol.repository;

import com.example.project_jjol.model.LecturePage;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LecturePageMapper {

    @Select("SELECT * FROM LecturePage WHERE lecture_id = #{lectureId} AND user_id = #{userId} AND chapter_id = #{chapterId}")
    LecturePage findLecturePage(@Param("lectureId") int lectureId, @Param("userId") String userId, @Param("chapterId") int chapterId);

    @Insert("INSERT INTO LecturePage (lecture_id, user_id, chapter_id, start_time, last_viewed) VALUES (#{lectureId}, #{userId}, #{chapterId}, #{startTime}, NOW())")
    void saveLecturePage(LecturePage lecturePage);

    @Update("UPDATE LecturePage SET start_time = #{startTime}, last_viewed = NOW() WHERE lecture_id = #{lectureId} AND user_id = #{userId} AND chapter_id = #{chapterId}")
    void updateLecturePage(LecturePage lecturePage);
    
    @Update("UPDATE LecturePage SET start_time = 0, last_viewed = CURRENT_TIMESTAMP WHERE lecture_id = #{lectureId} AND user_id = #{userId}")
    void resetLecturePages(@Param("lectureId") int lectureId, @Param("userId") String userId);
    
    @Select("SELECT COUNT(*) > 0 FROM LecturePage WHERE user_id = #{userId} AND chapter_id = #{chapterId}")
    boolean hasUserViewedChapter(@Param("userId") String userId, @Param("chapterId") int chapterId);


}
