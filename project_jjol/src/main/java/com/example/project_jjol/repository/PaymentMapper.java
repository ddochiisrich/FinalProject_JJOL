package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.project_jjol.model.Payment;
import com.example.project_jjol.model.User;


@Mapper
public interface PaymentMapper {
	@Select("SELECT * FROM payment")
	List<Payment> getPayment();
	
	@Insert("INSERT INTO payment (pay_date, pay_way, price, user_id, lecture_id, lecture_title, merchant_uid) "
			+ "VALUES (NOW(), 'kakaopay', #{price}, #{userId}, #{lectureId}, #{lectureTitle}, #{merchantUid})")
	@Options(useGeneratedKeys = true, keyProperty = "payCode")
	void savePayment(Payment payment);
	
	@Update("UPDATE user SET point = #{point} WHERE user_id = #{userId}")
	void updatePoint(User user);
	
	@Select("SELECT lecture_price - (lecture_price * lecture_discount / 100) "
			+ "FROM lecture WHERE lecture_id = #{lectureId}")
	int discountedPrice(int lectureId);
	
	@Select("SELECT point FROM user WHERE user_id = #{userId} LIMIT 1")
	int userPoint(String userId);
}
