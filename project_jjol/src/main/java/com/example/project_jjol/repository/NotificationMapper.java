package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.*;

import com.example.project_jjol.model.Notification;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Select("SELECT * FROM notification WHERE id = #{id}")
    Notification findById(Long id);

    @Select("SELECT * FROM notification")
    List<Notification> findAll();

    @Insert("INSERT INTO notification(subject, exam_date) VALUES(#{subject}, #{examDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Notification notification);

    @Update("UPDATE notification SET subject = #{subject}, exam_date = #{examDate} WHERE id = #{id}")
    void update(Notification notification);

    @Delete("DELETE FROM notification WHERE id = #{id}")
    void delete(Long id);
}

