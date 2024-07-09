package com.example.project_jjol.repository;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Notification;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface NotificationMapper {

	
	
    // 사용자 ID를 기반으로 다가오는 시험을 검색하는 메서드 추가
	// @Select("SELECT * FROM notification WHERE user_name =#{userId}")
	@Select("SELECT * FROM notification WHERE user_name = #{userId} AND exam_date BETWEEN #{today} AND #{upcomingDate}")
    List<Notification> findByUsernameAndExamDateBetween(
        @Param("userId") String userId, LocalDate today, LocalDate upcomingDate);

	@Insert("INSERT INTO notification (id, subject, user_name, exam_date) VALUES (#{id}, #{subject}, #{userName}, #{examDate})")
    void insert(Notification notification);
	
	@Select("SELECT * FROM notification WHERE id = #{id}")
    Notification findById(Long id);

    // 가장 임박한 알림 가져오는 메서드 추가
	@Select("SELECT *, DATEDIFF(exam_date, CURDATE()) AS daysUntilExam FROM notification WHERE user_name = #{userId} AND exam_date >= CURDATE() ORDER BY exam_date ASC LIMIT 1")
    Notification findMostUrgentNotification(@Param("userId") String userId);
}