package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Lecture;

import java.util.List;

@Mapper
public interface LectureMapper {

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
}
