package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (user_id, pass, name, email, phone, role, point, reg_date) VALUES " +
            "(#{userId}, #{pass}, #{name}, #{email}, #{phone}, #{role}, #{point}, #{regDate})")
    void saveUser(User user);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findById(String userId);
}
