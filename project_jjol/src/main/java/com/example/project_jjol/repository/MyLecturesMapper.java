package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Lecture;

@Mapper
public interface MyLecturesMapper {
	@Select("SELECT * FROM Lecture WHERE instructor_id = #{userId}")
    List<Lecture> findMyLecturesByUserId(@Param("userId") String userId);
}