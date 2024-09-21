package com.example.project_jjol.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

	public String createCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000); // 6자리 난수설정
		return String.valueOf(code);
	}
	
}
