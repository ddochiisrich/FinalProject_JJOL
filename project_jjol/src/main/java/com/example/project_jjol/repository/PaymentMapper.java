package com.example.project_jjol.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.model.User;


@Mapper
public interface PaymentMapper {
	@Insert("INSERT INTO Payment (pay_date, pay_way, price, user_id, lecture_id, lecture_title, merchant_uid) "
			+ "VALUES (NOW(), 'kakaopay', #{price}, #{userId}, #{lectureId}, #{lectureTitle}, #{merchantUid})")
	@Options(useGeneratedKeys = true, keyProperty = "payCode")
	void savePayment(Payment payment);
	
	@Update("UPDATE User SET point = #{point} WHERE user_id = #{userId}")
	void updatePoint(User user);
	
	@Select("SELECT lecture_price - (lecture_price * lecture_discount / 100) "
			+ "FROM Lecture WHERE lecture_id = #{lectureId}")
	int discountedPrice(int lectureId);
	
	@Select("SELECT point FROM User WHERE user_id = #{userId} LIMIT 1")
	int userPoint(String userId);
}
