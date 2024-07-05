package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Chapter;

@Mapper
public interface ChapterMapper {

    @Insert("INSERT INTO Chapter (lecture_id, chapter_title, chapter_description, chapter_url, chapter_order) VALUES " +
            "(#{lectureId}, #{chapterTitle}, #{chapterDescription}, #{chapterUrl}, #{chapterOrder})")
    void saveChapter(Chapter chapter);

    @Select("SELECT * FROM Chapter WHERE lecture_id = #{lectureId} ORDER BY chapter_order")
    List<Chapter> findByLectureId(int lectureId);

    @Select("SELECT c.* FROM Chapter c JOIN LecturePage lp ON c.chapter_id = lp.chapter_id " +
            "WHERE lp.lecture_id = #{lectureId} AND lp.user_id = #{userId} ORDER BY lp.last_viewed DESC LIMIT 1")
    Chapter findLastViewedChapterByLectureIdAndUserId(@Param("lectureId") int lectureId, @Param("userId") String userId);
    
    @Select("SELECT * FROM Chapter WHERE chapter_id = #{chapterId}")
    Chapter findById(@Param("chapterId") int chapterId);
    
}
