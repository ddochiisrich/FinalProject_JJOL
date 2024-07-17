package com.example.project_jjol.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.DataSharing;
import com.example.project_jjol.repository.DataSharingMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataSharingService {

    @Autowired
    private DataSharingMapper datasharingMapper;
    @Autowired
    private S3Service s3Service;

    public List<DataSharing> findAll() {
        return datasharingMapper.findAll();
    }

    // DB테이블에 게시 글 정보를 추가하는 메서드
    public DataSharing InstructorWrite(DataSharing datasharing) {
        log.info("DataSharingService: InstructorWirte(DataSharing datasharing)");
        datasharingMapper.insertData(datasharing);
        return datasharing;
    }

    // DB테이블에서 게시글 정보를 가져오는 메서드
    public List<DataSharing> getDataSharing() {
        return datasharingMapper.getDataSharing();
    }

    // 글 no를 통해 데이터 조회
    public DataSharing findByNo(int no) {
        return datasharingMapper.findByNo(no);
    }

    // 글 no를 통해 데이터 삭제
    public void deleteDataSharing(int no) {
        log.info("DataSharingService : deleteDataSharing(int no)");
        datasharingMapper.deleteDataSharing(no);
    }

    // 글 수정하기
    public void updateDataSharing(DataSharing datasharing) {
        datasharingMapper.updateDataSharing(datasharing);
    }

    // 파일 업로드
    public String storeFile(MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    // 파일 경로 반환 메서드
    public String getFileUrl(String fileName) {
        return s3Service.getFileUrl(fileName);
    }

    // 검색 기능
    public List<DataSharing> searchDataSharing(String searchQuery) {
        return datasharingMapper.searchDataSharing(searchQuery);
    }
}
