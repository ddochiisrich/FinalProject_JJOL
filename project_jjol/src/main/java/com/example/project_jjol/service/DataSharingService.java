package com.example.project_jjol.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.DataSharing;
import com.example.project_jjol.repository.DataSharingMapper;
import com.example.project_jjol.repository.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataSharingService {

	@Autowired
	private DataSharingMapper datasharingMapper;
	private UserMapper userMapper;
	private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

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

	// 파일 업로드
	public DataSharingService() throws IOException {
		Files.createDirectories(this.fileStorageLocation);
	}

	public String storeFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation);
		return fileName;
	}
	
	//파일 경로 반환 메서드
	public Path getFilePath(String fileName) {
		return this.fileStorageLocation.resolve(fileName).normalize();
	}
}
