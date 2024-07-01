package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Chapter;

import java.util.List;

@Mapper
public interface ChapterMapper {

    @Insert("INSERT INTO Chapter (lecture_id, chapter_title, chapter_description, chapter_url, chapter_order) VALUES " +
            "(#{lectureId}, #{chapterTitle}, #{chapterDescription}, #{chapterUrl}, #{chapterOrder})")
    void saveChapter(Chapter chapter);

    @Select("SELECT * FROM Chapter WHERE lecture_id = #{lectureId} ORDER BY chapter_order")
    List<Chapter> findByLectureId(int lectureId);
}
