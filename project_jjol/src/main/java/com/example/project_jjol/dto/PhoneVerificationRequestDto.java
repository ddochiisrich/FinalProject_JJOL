package com.example.project_jjol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneVerificationRequestDto {

	private String phoneNumber;			// 사용자의 핸드폰 번호
	private String verificationCode;		// 입력받은 인증 코드
	
}
