package com.example.project_jjol.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

	private final StringRedisTemplate redisTemplate;
	
	public RedisUtil(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	// 데이터를 저장하고 유효 기간 설정
	public void setDataExpire(String key, String value, long duration) {
		redisTemplate.opsForValue().set(key, value, duration, TimeUnit.SECONDS);
	}
	
	// 저장된 데이터를 가져오기
	public String getData(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	// 저장된 데이터를 삭제
	public void deleteData(String key) {
		redisTemplate.delete(key);
	}
}
