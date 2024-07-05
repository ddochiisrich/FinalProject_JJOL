package com.example.project_jjol.repository;

import com.example.project_jjol.model.Certificate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CertificateMapper {

    @Insert("INSERT INTO Certificate (user_id, lecture_id, issue_date) VALUES (#{userId}, #{lectureId}, #{issueDate})")
    void insertCertificate(Certificate certificate);

    @Select("SELECT * FROM Certificate WHERE user_id = #{userId} AND lecture_id = #{lectureId}")
    List<Certificate> findCertificatesByUserIdAndLectureId(@Param("userId") String userId, @Param("lectureId") int lectureId);
}
