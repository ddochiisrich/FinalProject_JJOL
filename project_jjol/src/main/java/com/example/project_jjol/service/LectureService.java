package com.example.project_jjol.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.Lecture;
import com.example.project_jjol.model.LectureReview;
import com.example.project_jjol.repository.ChapterMapper;
import com.example.project_jjol.repository.LectureMapper;
import com.example.project_jjol.repository.LecturePageMapper;

@Service
public class LectureService {

    @Autowired
    private LectureMapper lectureMapper;

    @Autowired
    private ChapterMapper chapterMapper;
    
    @Autowired
    private LecturePageMapper lecturePageMapper;

    @Autowired
    private LectureReviewService lectureReviewService;

    @Autowired
    private LectureApplicationService lectureApplicationService;

    @Autowired
    private S3Service s3Service;

    @Transactional(readOnly = true)
    public List<Lecture> searchLectures(String keyword) {
        return lectureMapper.searchLectures(keyword);
    }

    @Transactional
    public void saveLectureAndChapters(Lecture lecture, List<MultipartFile> chapterFiles, List<String> chapterTitles, List<String> chapterDescriptions, List<Integer> chapterOrders) throws IOException {
        // 1. 강의 저장
        lectureMapper.saveLecture(lecture);
        int lectureId = lecture.getLectureId();

        // 2. 각 챕터 저장
        for (int i = 0; i < chapterFiles.size(); i++) {
            MultipartFile file = chapterFiles.get(i);
            String fileUrl = s3Service.uploadFile(file);

            Chapter chapter = new Chapter();
            chapter.setLectureId(lectureId);
            chapter.setChapterTitle(chapterTitles.get(i));
            chapter.setChapterDescription(chapterDescriptions.get(i));
            chapter.setChapterUrl(fileUrl);
            chapter.setChapterOrder(chapterOrders.get(i));

            chapterMapper.saveChapter(chapter);
        }
    }

    @Transactional(readOnly = true)
    public List<Lecture> getAllLectures() {
        return lectureMapper.findAll();
    }

    @Transactional(readOnly = true)
    public Lecture getLectureById(int id) {
        return lectureMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Chapter> getChaptersByLectureId(int lectureId) {
        return chapterMapper.findByLectureId(lectureId);
    }

    @Transactional(readOnly = true)
    public double getAverageRating(int lectureId) {
        List<LectureReview> reviews = lectureReviewService.getReviewsByLectureId(lectureId);
        return reviews.stream().mapToDouble(LectureReview::getRating).average().orElse(0.0);
    }

    @Transactional(readOnly = true)
    public long getNumberOfStudents(int lectureId) {
        return lectureApplicationService.getNumberOfStudentsByLectureId(lectureId);
    }
    
    @Transactional(readOnly = true)
    public Chapter getLastViewedChapter(int lectureId, String userId) {
        return chapterMapper.findLastViewedChapterByLectureIdAndUserId(lectureId, userId);
    }
    
    @Transactional
    public void resetLecturePages(int lectureId, String userId) {
        lecturePageMapper.resetLecturePages(lectureId, userId);
    }

    @Transactional(readOnly = true)
    public boolean hasUserViewedChapter(String userId, int chapterId) {
        return lecturePageMapper.hasUserViewedChapter(userId, chapterId);
    }
    
    public List<String> getLecturesByInstructorId(String instructorId) {
        return lectureMapper.selectTitlesByInstructorId(instructorId);
    }
    
}
