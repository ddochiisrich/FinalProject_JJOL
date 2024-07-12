package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;

@Mapper
public interface MyLecturesMapper {
	@Select("SELECT * FROM Lecture WHERE instructor_id = #{userId}")
    List<Lecture> findMyLecturesByUserId(@Param("userId") String userId);
	
	@Delete("DELETE FROM Lecture WHERE lecture_id = #{lectureId}")
	void deleteLecture(@Param("lectureId") int lectureId);
	
	@Select("SELECT * FROM Lecture WHERE lecture_id = #{lectureId}")
	Lecture findLectureByLectureId(@Param("lectureId") int lectureId);
	
	@Select("SELECT * FROM Chapter WHERE lecture_id = #{lectureId}")
	List<Chapter> findChaptersByLectureId(@Param("lectureId") int lectureId);
	
	@Update("<script>"
			+ "UPDATE lecture SET "
			+ "lecture_title = #{lectureTitle}, "
			+ "lecture_short_description = #{lectureShortDescription}, "
			+ "lecture_long_description = #{lectureLongDescription}, "
			+ "<if test='lectureThumbnailVideo != null'>lecture_thumbnail_video = #{lectureThumbnailVideo}, </if>"
			+ "<if test='lectureThumbnailImage != null'>lecture_thumbnail_image = #{lectureThumbnailImage}, </if>"
			+ "lecture_level = #{lectureLevel}, "
			+ "lecture_price = #{lecturePrice}, "
			+ "lecture_discount = #{lectureDiscount} "
			+ "WHERE lecture_id = #{lectureId}"
			+ "</script>")
	void updateLecture(Lecture lecture);
	
	@Update("<script>"
			+ "UPDATE Chapter SET "
			+ "chapter_title = #{chapterTitle}, "
			+ "chapter_description = #{chapterDescription}, "
			+ "<if test='chapterUrl != null'>chapter_url = #{chapterUrl}, </if>"
			+ "chapter_order = #{chapterOrder} "
			+ "WHERE chapter_id = #{chapterId}"
			+ "</script>")
	void updateChapter(Chapter chapter);
}