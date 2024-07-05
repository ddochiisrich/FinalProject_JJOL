package com.example.project_jjol.service;

import com.example.project_jjol.model.LectureNote;
import com.example.project_jjol.repository.LectureNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureNoteService {

    @Autowired
    private LectureNoteMapper lectureNoteMapper;

    public void saveLectureNote(LectureNote lectureNote) {
        lectureNoteMapper.saveLectureNote(lectureNote);
    }

    public List<LectureNote> getLectureNotesByLectureIdAndUserId(int lectureId, String userId) {
        return lectureNoteMapper.findByLectureIdAndUserId(lectureId, userId);
    }

    public void deleteLectureNoteById(int noteId) {
        lectureNoteMapper.deleteLectureNoteById(noteId);
    }
}
