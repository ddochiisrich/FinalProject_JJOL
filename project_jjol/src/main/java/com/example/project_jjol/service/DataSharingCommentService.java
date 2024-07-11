package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.DataSharingComment;
import com.example.project_jjol.repository.DataSharingCommentMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataSharingCommentService {

	@Autowired
	private DataSharingCommentMapper datasharingcommentMapper;
	
	//댓글 정보 추가
	public DataSharingComment insertdatacomment(DataSharingComment datasharingcomment) {
		datasharingcommentMapper.insertdatacomment(datasharingcomment);
		return datasharingcomment;
	}
	
	//글 no를 통해 데이터 조회
	public List<DataSharingComment> getCommentsByDataNo(int no){
		return datasharingcommentMapper.getCommentsByDataNo(no);
	}
}
