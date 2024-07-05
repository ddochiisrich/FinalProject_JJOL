package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.example.project_jjol.model.DataSharing;


@Mapper
public interface DataSharingMapper {

	@Select("SELECT data_name, data_title, data_content, data_date, data_pw, data_file FROM datasharing")
	List<DataSharing> findAll();
	
	 @Insert("INSERT INTO datasharing (data_name, data_title, data_content, data_date, data_pw, data_file) " +
	            "VALUES (#{dataName}, #{dataTitle}, #{dataContent}, #{dataDate}, #{dataPw}, #{dataFile})")
	    void insertData(DataSharing data);
	 
	 //글 가져오기
	 @Select("SELECT * FROM datasharing")
	 List<DataSharing> getDataSharing();
	}
