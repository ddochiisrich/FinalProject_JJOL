package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.example.project_jjol.model.Payment;

@Mapper
public interface PaymentMapper {
	@Insert("INSERT INTO payment (pay_date, pay_way, price, user_id, lecture_id) "
			+ "VALUES(NOW(), 'kakaopay', #{price}, #{userId}, #{lectureId})")
	public void addPayment(Integer price, String userId, Integer lectureId);
}
