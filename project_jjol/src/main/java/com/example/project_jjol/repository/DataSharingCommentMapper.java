package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.project_jjol.model.DataSharingComment;

@Mapper
public interface DataSharingCommentMapper {

	// 댓글 추가
	@Insert("INSERT INTO datasharingcomment (dsc_no, ddno, dsc_id, dsc_content, dsc_writer, dsc_time)"
			+ "VALUES (#{dscNo}, #{ddNo}, #{dscId}, #{dscContent}, #{dscWriter}, #{dscTime})")
	@Options(useGeneratedKeys = true, keyProperty = "dscNo")
	void insertdatacomment(DataSharingComment datasharingComment);

	// 특정 데이터 번호에 해당하는 모든 댓글 조회
	@Select("SELECT dsc_no, ddno, dsc_content, dsc_writer, dsc_time " + "FROM datasharingcomment "
			+ "WHERE ddno = #{dataNo} " // ddno는 datasharing 테이블의 data_no에 해당하는 외래 키
			+ "ORDER BY dsc_time DESC")
	List<DataSharingComment> getCommentsByDataNo(@Param("dataNo") int dataNo);
}
