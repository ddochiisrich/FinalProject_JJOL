package com.example.project_jjol.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project_jjol.dto.PhoneVerificationRequestDto;
import com.example.project_jjol.model.Certificate;
import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.User;
import com.example.project_jjol.repository.CertificateMapper;
import com.example.project_jjol.repository.LectureApplicationMapper;
import com.example.project_jjol.repository.UserMapper;
import com.example.project_jjol.util.RedisUtil;
import com.example.project_jjol.util.SmsUtil;
import com.example.project_jjol.util.ValidationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SmsUtil smsUtil;
    private final RedisUtil redisUtil;
    private final ValidationUtil validationUtil;
    private final PasswordService passwordService;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private LectureApplicationMapper lectureApplicationMapper;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CertificateMapper certificateMapper;
    
    public void registerUser(User user) {
        System.out.println("registerUser 메서드 호출됨"); // 메서드 진입 확인용 로그

        // 비밀번호 암호화
        String encodedPassword = passwordService.encodePassword(user.getPass());
        System.out.println("Encoded Password: " + encodedPassword);

        // 암호화된 비밀번호 저장
        user.setPass(encodedPassword);

        // 사용자 저장
        userMapper.saveUser(user);
        System.out.println("사용자 저장 완료");
    }
    
    public User findByEmailAndProvider(String email, String provider) {
        return userMapper.findByEmailAndProvider(email, provider);
    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

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
    
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    
    // 핸드폰 인증 코드 전송
    public ResponseEntity<?> sendSmsForSignupVerification(String phoneNumber){
    	String formattedPhoneNumber = phoneNumber.replaceAll("-", "");
    	
    	// 인증 코드 생성
    	String verificationCode = validationUtil.createCode();
    	
    	// 인증 코드 전송
    	smsUtil.sendOne(formattedPhoneNumber, verificationCode);
    	
    	// Redis에 인증 코드 저장 ( 5분동안 유효 )
    	redisUtil.setDataExpire(formattedPhoneNumber, verificationCode, 60 * 5L);
    	
    	return ResponseEntity.ok("인증 코드가 전송되었습니다.");
    }
    
    // 핸드폰 번호 인증 코드 검증
    public ResponseEntity<Map<String, String>> verifyPhoneNumber(PhoneVerificationRequestDto requestDto) {
        String phoneNumber = requestDto.getPhoneNumber().replaceAll("-", "");
        String inputCode = requestDto.getVerificationCode();

        // Redis에서 저장된 인증 코드 가져오기
        String storedCode = redisUtil.getData(phoneNumber);

        // 인증코드 검증
        Map<String, String> response = new HashMap<>();
        if (storedCode == null || !storedCode.equals(inputCode)) {
            response.put("message", "인증 코드가 일치하지 않거나 만료되었습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 인증 완료 후 인증 코드 삭제
        redisUtil.deleteData(phoneNumber);

        response.put("message", "휴대폰 번호 인증이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }
    
 // 비밀번호 재설정 요청
    public boolean requestPasswordReset(String userId, String name, String phone) {
        // 사용자 조회
        User user = userMapper.findById(userId);
        if (user != null && user.getName().equals(name) && user.getPhone().equals(phone)) {
            // Redis에 인증 코드 생성 및 저장
            String verificationCode = validationUtil.createCode();
            redisUtil.setDataExpire(phone, verificationCode, 60 * 5L);
            smsUtil.sendOne(phone, verificationCode);
            return true;
        }
        return false;
    }

    // 비밀번호 재설정
    public boolean resetPassword(String userId, String phone, String verificationCode, String newPassword) {
        String storedCode = redisUtil.getData(phone);
        if (storedCode != null && storedCode.equals(verificationCode)) {
            // 인증 성공, 비밀번호 재설정
            String encodedPassword = passwordService.encodePassword(newPassword);
            User user = userMapper.findById(userId);
            if (user != null) {
                user.setPass(encodedPassword);
                userMapper.updateUser(user);
                redisUtil.deleteData(phone); // 인증 코드 삭제
                return true;
            }
        }
        return false;
    }

}
