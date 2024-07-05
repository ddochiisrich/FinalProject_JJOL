package com.example.project_jjol.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project_jjol.model.Chapter;
import com.example.project_jjol.model.LecturePage;
import com.example.project_jjol.repository.ChapterMapper;
import com.example.project_jjol.repository.LecturePageMapper;

@Service
public class LecturePageService {

    @Autowired
    private LecturePageMapper lecturePageMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    public LecturePage getLecturePage(int lectureId, String userId, int chapterId) {
        try {
            return lecturePageMapper.findLecturePage(lectureId, userId, chapterId);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            throw new RuntimeException("Failed to get lecture page", e);
        }
    }

    public void saveOrUpdateLecturePage(LecturePage lecturePage) {
        try {
            LecturePage existingPage = lecturePageMapper.findLecturePage(lecturePage.getLectureId(), lecturePage.getUserId(), lecturePage.getChapterId());
            if (existingPage != null) {
                lecturePageMapper.updateLecturePage(lecturePage);
            } else {
                lecturePageMapper.saveLecturePage(lecturePage);
            }
            lecturePageMapper.updateLastChapterOrder(lecturePage.getLectureId(), lecturePage.getUserId(), lecturePage.getChapterId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save or update lecture page", e);
        }
    }


    public Map<String, Object> getLastViewedChapter(int lectureId, String userId) {
        LecturePage lastViewedPage = lecturePageMapper.findLastViewedPage(lectureId, userId);
        if (lastViewedPage == null) {
            return null;
        }
        Chapter chapter = chapterMapper.findById(lastViewedPage.getChapterId());
        
        System.out.println(chapter);

        Map<String, Object> response = new HashMap<>();
        response.put("chapterOrder", chapter.getChapterOrder());
        response.put("startTime", lastViewedPage.getStartTime());
        response.put("chapterUrl", chapter.getChapterUrl());
        return response;
    }

    @Transactional
    public void resetLecturePages(int lectureId, String userId) {
        lecturePageMapper.resetLecturePages(lectureId, userId);
    }
}


