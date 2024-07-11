package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Lecture;

@Mapper
public interface LectureMapper {

	@Select("SELECT * FROM Lecture WHERE lecture_title LIKE CONCAT('%', #{keyword}, '%') OR lecture_short_description LIKE CONCAT('%', #{keyword}, '%')")
	List<Lecture> searchLectures(@Param("keyword") String keyword);

	
    @Insert("INSERT INTO Lecture (lecture_title, lecture_short_description, lecture_long_description, " +
            "lecture_thumbnail_video, lecture_thumbnail_image, lecture_level, lecture_price, " +
            "lecture_discount, lecture_like, instructor_id, instructor_name) VALUES " +
            "(#{lectureTitle}, #{lectureShortDescription}, #{lectureLongDescription}, " +
            "#{lectureThumbnailVideo}, #{lectureThumbnailImage}, #{lectureLevel}, #{lecturePrice}, " +
            "#{lectureDiscount}, #{lectureLike}, #{instructorId}, #{instructorName})")
    @Options(useGeneratedKeys = true, keyProperty = "lectureId")
    void saveLecture(Lecture lecture);

    @Select("SELECT * FROM Lecture")
    List<Lecture> findAll();

    @Select("SELECT * FROM Lecture WHERE lecture_id = #{id}")
    Lecture findById(int id);
    
    @Select("SELECT lecture_title FROM Lecture WHERE instructor_id = #{id}")
    List<String> selectTitlesByInstructorId(@Param("id") String id);
    
    
}
