package com.example.project_jjol.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_jjol.model.Chat;
import com.example.project_jjol.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

	
	private final ChatService chatService;
	
	@GetMapping("/chatting")
	public List<Chat> chatList(){
		return chatService.ChatList();
	}
	
	 
}
