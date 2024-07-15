package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.project_jjol.model.User;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM user WHERE email = #{email} AND provider = #{provider}")
    User findByEmailAndProvider(@Param("email") String email, @Param("provider") String provider);

    @Insert("INSERT INTO user (user_id, pass, name, email, phone, role, point, reg_date, provider) VALUES " +
            "(#{userId}, #{pass}, #{name}, #{email}, #{phone}, #{role}, #{point}, #{regDate}, #{provider})")
    void saveUser(User user);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findById(String userId);
    
    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(@Param("phone") String phone);
    
    @Update("UPDATE user SET name = #{name}, email = #{email}, phone = #{phone}, pass = #{pass} WHERE user_id = #{userId}")
    void updateUser(User user);
}
