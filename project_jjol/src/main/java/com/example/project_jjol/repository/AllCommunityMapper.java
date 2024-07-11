package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.project_jjol.model.AllCommunity;

@Mapper
public interface AllCommunityMapper {


	// 글 목록 no순으로 불러오기
	@Select("SELECT * FROM allcommunity WHERE allc_no = #{no}")
	AllCommunity findByNo(@Param("no") int no);

	// 글 작성해서 올리기
	@Insert("INSERT INTO allcommunity (allc_name, allc_title, allc_content, allc_date, allc_pass, allc_file)"
			+ "VALUES (#{allcName}, #{allcTitle}, #{allcContent}, #{allcDate}, #{allcPass}, #{allcFile})")
	@Options(useGeneratedKeys = true, keyProperty = "allcNo")
	void inserAllc(AllCommunity allcommunity);

	// 글 삭제
	@Delete("DELETE FROM allcommunity WHERE allc_no = #{no}")
	void deleteAllCommunity(int no);

	// 글 수정하기
	@Update("UPDATE allcommunity SET allc_title = #{allcTitle}, allc_content = #{allcContent} WHERE allc_no = #{allcNo}")
	void updateAllCommunity(AllCommunity allcommunity);

	//페이징처리
	List<AllCommunity> selectCommunityList(@Param("startRow") int startRow, @Param("pageSize") int pageSize,
			@Param("type") String type, @Param("keyword") String keyword);
	
	//글 목록수
	int getCommunityCount(@Param("type") String type, @Param("keyword") String keyword);

	//글 리스트
	 @Select("SELECT * FROM allcommunity ORDER BY allc_no DESC")
	    List<AllCommunity> getAllCommunity();
	 
	 List<AllCommunity> selectCommunityListForAll(@Param("startRow") int startRow, @Param("pageSize") int pageSize, @Param("keyword") String keyword);

	    int countCommunityListForAll(@Param("keyword") String keyword);

	    int getCommunityCountForAll(@Param("keyword") String keyword);


}
