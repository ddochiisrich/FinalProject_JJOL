package com.example.project_jjol.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
@RequiredArgsConstructor
public class SmsUtil {

	@Value("${coolsms.api.key}")
	private String apiKey;
	
	@Value("${coolsms.api.secret}")
	private String apiSecret;
	
	private DefaultMessageService messageService;
	
	// API 키와 보인키 설정 메서드
	@PostConstruct
	private void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}
	
	// SMS 전송 메서드
	public SingleMessageSentResponse sendOne(String to, String verifivationCode) {
		Message message = new Message();
		// 발신번호는 -를 제외한 01012341234과 같은 형태로 입력
		message.setFrom("01071216870");
		message.setTo(to);
		message.setText("[JJol EDU] 아래의 인증번호를 입력해주세요\n" + verifivationCode);
		
		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		return response;
	}
}
