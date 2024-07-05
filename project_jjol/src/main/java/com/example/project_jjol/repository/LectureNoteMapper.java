package com.example.project_jjol.repository;

import com.example.project_jjol.model.LectureNote;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LectureNoteMapper {

    @Insert("INSERT INTO LectureNote (lecture_id, user_id, note_title, note_content, created_at) " +
            "VALUES (#{lectureId}, #{userId}, #{noteTitle}, #{noteContent}, NOW())")
    void saveLectureNote(LectureNote lectureNote);

    @Select("SELECT * FROM LectureNote WHERE lecture_id = #{lectureId} AND user_id = #{userId}")
    List<LectureNote> findByLectureIdAndUserId(@Param("lectureId") int lectureId, @Param("userId") String userId);

    @Delete("DELETE FROM LectureNote WHERE note_id = #{noteId}")
    void deleteLectureNoteById(@Param("noteId") int noteId);
}
