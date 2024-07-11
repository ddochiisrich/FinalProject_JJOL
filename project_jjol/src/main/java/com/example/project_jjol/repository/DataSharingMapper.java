package com.example.project_jjol.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.project_jjol.model.DataSharing;


@Mapper
public interface DataSharingMapper {

    @Select("SELECT * FROM datasharing WHERE data_no = #{no}")
    DataSharing findByNo(int no);

    @Select("SELECT data_no, data_name, data_title, data_content, data_date, data_pw, data_file FROM datasharing")
    List<DataSharing> findAll();

    @Insert("INSERT INTO datasharing (data_name, data_title, data_content, data_date, data_pw, data_file) " +
            "VALUES (#{dataName}, #{dataTitle}, #{dataContent}, #{dataDate}, #{dataPw}, #{dataFile})")
    @Options(useGeneratedKeys = true, keyProperty="dataNo")
    void insertData(DataSharing datasharing);

    @Select("SELECT * FROM datasharing")
    List<DataSharing> getDataSharing();
    
    @Delete("DELETE FROM datasharing WHERE data_no = #{no}")
    void deleteDataSharing(int no);
    
    @Update("UPDATE datasharing SET data_title = #{dataTitle}, data_content = #{dataContent} WHERE data_no = #{dataNo}")
    void updateDataSharing(DataSharing datasharing);
    
    
}
