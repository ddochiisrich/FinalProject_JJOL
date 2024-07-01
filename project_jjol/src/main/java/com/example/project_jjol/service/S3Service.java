package com.example.project_jjol.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.config.S3Config;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    @Autowired
    public S3Service(S3Client s3Client, S3Config s3Config) {
        this.s3Client = s3Client;
        this.bucketName = s3Config.getBucketName();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File convertedFile = convertMultiPartToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .metadata(metadata)
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(convertedFile.getAbsolutePath()));

            return getFileUrl(fileName);
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3", e);
        } finally {
            convertedFile.delete();
        }
    }

    public String getFileUrl(String fileName) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
