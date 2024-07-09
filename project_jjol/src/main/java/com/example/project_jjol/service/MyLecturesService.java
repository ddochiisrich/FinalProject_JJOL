package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.repository.MyLecturesMapper;
import com.example.project_jjol.repository.UserMapper;

@Service
public class MyLecturesService {
	
	@Autowired
	private MyLecturesMapper myLecturesMapper;
	
	public List<Lecture> findMyLecturesByUserId(String userId) {
		return myLecturesMapper.findMyLecturesByUserId(userId);
	}
	
	public void deleteLecture(int lectureId) {
		myLecturesMapper.deleteLecture(lectureId);
	}
	
	public Lecture findLectureByLectureId(int lectureId) {
		return myLecturesMapper.findLectureByLectureId(lectureId);
	}
	
	public List<Chapter> findChaptersByLectureId(int lectureId) {
		return myLecturesMapper.findChaptersByLectureId(lectureId);
	}
	
	public void updateLecture(Lecture lecture) {
		myLecturesMapper.updateLecture(lecture);
	}
}
