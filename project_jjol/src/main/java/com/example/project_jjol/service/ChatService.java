package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Chat;
import com.example.project_jjol.repository.ChatMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatService {

	@Autowired
	private ChatMapper chatMapper;
	
	public List<Chat> ChatList(){
		log.info("Service : chatList");
		return chatMapper.ChatList();
	}
}
