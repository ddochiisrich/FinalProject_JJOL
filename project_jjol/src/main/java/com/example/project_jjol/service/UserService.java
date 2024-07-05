package com.example.project_jjol.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Certificate;
import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.User;
import com.example.project_jjol.repository.CertificateMapper;
import com.example.project_jjol.repository.LectureApplicationMapper;
import com.example.project_jjol.repository.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private LectureApplicationMapper lectureApplicationMapper;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CertificateMapper certificateMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    public User findById(String userId) {
        return userMapper.findById(userId);
    }

    public List<Lecture> getLecturesByUserId(String userId) {
        return lectureApplicationMapper.findLecturesByUserId(userId);
    }

    public boolean hasCompletedAllChapters(String userId, int lectureId) {
        List<Chapter> chapters = lectureService.getChaptersByLectureId(lectureId);
        for (Chapter chapter : chapters) {
            if (!lectureService.hasUserViewedChapter(userId, chapter.getChapterId())) {
                return false;
            }
        }
        return true;
    }

    public void issueCertificate(String userId, int lectureId) {
        if (hasCompletedAllChapters(userId, lectureId)) {
            Certificate certificate = new Certificate();
            certificate.setUserId(userId);
            certificate.setLectureId(lectureId);
            certificate.setIssueDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            certificateMapper.insertCertificate(certificate);
        } else {
            throw new IllegalStateException("모든 챕터를 완료하지 않았습니다.");
        }
    }

    public Certificate getCertificate(String userId, int lectureId) {
        List<Certificate> certificates = certificateMapper.findCertificatesByUserIdAndLectureId(userId, lectureId);
        if (certificates.isEmpty()) {
            return null;
        }
        return certificates.get(0);
    }

    public boolean hasIssuedCertificate(String userId, int lectureId) {
        Certificate certificate = getCertificate(userId, lectureId);
        return certificate != null;
    }
}
