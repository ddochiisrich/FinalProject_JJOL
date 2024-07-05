package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentMapper {
    @Select("SELECT price FROM payment WHERE lecture_id = #{lectureId}")
    Integer getPriceByLectureId(Integer lectureId);
}