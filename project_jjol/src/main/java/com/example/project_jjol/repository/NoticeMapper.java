package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.project_jjol.model.Notice;

@Mapper
public interface NoticeMapper {
	@Select("SELECT * FROM notice ORDER BY created_date DESC")
    List<Notice> findAll();

    @Select("SELECT * FROM notice WHERE id = #{id}")
    Notice findById(Long id);

    @Insert("INSERT INTO notice (title, content, created_date, user_id) VALUES (#{title}, #{content}, NOW(), #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Notice notice);

    @Update("UPDATE notice SET title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Notice notice);

    @Delete("DELETE FROM notice WHERE id = #{id}")
    void delete(Long id);
}
