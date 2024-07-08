package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.repository.MyLecturesMapper;
import com.example.project_jjol.repository.UserMapper;

@Service
public class MyLecturesService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MyLecturesMapper myLecturesMapper;
	
	public List<Lecture> getMyLecturesByUserId(String userId) {
		return myLecturesMapper.findMyLecturesByUserId(userId);
	}
}
