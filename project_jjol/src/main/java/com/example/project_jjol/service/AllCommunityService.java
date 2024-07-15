package com.example.project_jjol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.AllCommunity;
import com.example.project_jjol.model.CommunityComment;
import com.example.project_jjol.repository.AllCommunityMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AllCommunityService {
	@Autowired
	private AllCommunityMapper allcommunityMapper;

	// 글 no를 통해 데이터 조회
	public AllCommunity findByNo(int no) {
		return allcommunityMapper.findByNo(no);
	}

	// 글 작성
	public AllCommunity insertuser(AllCommunity allcommunity) {
		log.info("AllCommunityService: insertuser(AllCommunity allcommunity)");
		allcommunityMapper.inserAllc(allcommunity);
		return allcommunity;
	}

	// 글 삭제
	public void deleteAllCommunity(int no) {
		log.info("AllCommunityService: deleteAllCommunity(int no)");
		allcommunityMapper.deleteAllCommunity(no);
	}

	// 글 수정하기
	public void updateAllCommunity(AllCommunity allcommunity) {
		allcommunityMapper.updateAllCommunity(allcommunity);
	}

	// 페이지네이션
	public List<AllCommunity> selectCommunityList(int startRow, int pageSize, String type, String keyword) {
		return allcommunityMapper.selectCommunityList(startRow, pageSize, type, keyword);
	}

	// 조회수
	public int getCommunityCount(String type, String keyword) {
		return allcommunityMapper.getCommunityCount(type, keyword);
	}

	public List<AllCommunity> getAllCommunity() {
		return allcommunityMapper.getAllCommunity();
	}

	public List<AllCommunity> selectCommunityListForAll(int startRow, int pageSize, String keyword) {
		return allcommunityMapper.selectCommunityListForAll(startRow, pageSize, keyword);
	}

	public int countCommunityListForAll(String keyword) {
		return allcommunityMapper.countCommunityListForAll(keyword);
	}

	public int getCommunityCountForAll(String keyword) {
		return allcommunityMapper.getCommunityCountForAll(keyword);
	}

	public CommunityComment insertcommunitycomment(CommunityComment communitycomment) {
		allcommunityMapper.insertcommunitycomment(communitycomment);
		return communitycomment;
	}

	public List<CommunityComment> getCommentsByccNo(int no) {
		return allcommunityMapper.getCommentsByccNo(no);
	}

	// 댓글 삭제 메소드
	public boolean deleteCommunityComment(int no) {
		try {
			allcommunityMapper.deleteCommunityComment(no);
			return true;
		} catch (Exception e) {
			// 예외 처리 로직 추가 (예: 로그 남기기)
			return false;
		}
	}
}