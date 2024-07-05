package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.Chat;

@Mapper
public interface ChatMapper {

	@Select("SELECT * FROM chatting")
	List<Chat> ChatList();
	
	List<Chat> findAll();
}
