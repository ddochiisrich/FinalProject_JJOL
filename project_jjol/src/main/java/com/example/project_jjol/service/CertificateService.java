package com.example.project_jjol.service;

import com.example.project_jjol.model.Certificate;
import com.example.project_jjol.repository.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private CertificateMapper certificateMapper;

    public void issueCertificate(String userId, int lectureId) {
        // Check if certificate already exists
        List<Certificate> existingCertificates = certificateMapper.findCertificatesByUserIdAndLectureId(userId, lectureId);
        if (existingCertificates.isEmpty()) {
            Certificate certificate = new Certificate();
            certificate.setUserId(userId);
            certificate.setLectureId(lectureId);
            certificate.setIssueDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            certificateMapper.insertCertificate(certificate);
        } else {
            throw new IllegalStateException("이미 수료증이 발급되었습니다.");
        }
    }

    public Certificate getCertificate(String userId, int lectureId) {
        List<Certificate> certificates = certificateMapper.findCertificatesByUserIdAndLectureId(userId, lectureId);
        if (certificates.isEmpty()) {
            return null;
        }
        return certificates.get(0);
    }
}
