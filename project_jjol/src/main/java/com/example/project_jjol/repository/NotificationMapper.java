package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.project_jjol.model.Notification;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface NotificationMapper {

    @Select("SELECT * FROM notification WHERE user_name = #{userId} AND exam_date BETWEEN #{today} AND #{upcomingDate}")
    List<Notification> findByUsernameAndExamDateBetween(
        @Param("userId") String userId, LocalDate today, LocalDate upcomingDate);

    @Insert("INSERT INTO notification (id, subject, user_name, exam_date) VALUES (#{id}, #{subject}, #{userName}, #{examDate})")
    void insert(Notification notification);
    
    @Select("SELECT * FROM notification WHERE id = #{id}")
    Notification findById(Long id);

    @Select("SELECT *, DATEDIFF(exam_date, CURDATE()) AS daysUntilExam FROM notification WHERE user_name = #{userId} AND exam_date >= CURDATE() ORDER BY exam_date ASC LIMIT 1")
    Notification findMostUrgentNotification(@Param("userId") String userId);
    
    @Delete("DELETE FROM notification WHERE id = #{id}")
    void deleteById(@Param("id") Long id);

    @Select("SELECT * FROM notification WHERE user_name = #{userId}") 
    List<Notification> findByUserName(@Param("userId")String userId);

    @Select("SELECT * FROM notification WHERE id = #{notificationId}") 
    List<Notification> findByNotification(@Param("notificationId")String notificationId);

    @Update("UPDATE notification SET subject = #{subject}, exam_date = #{examDate} WHERE id = #{id}")
    void updateNotification(Notification notification);
}
