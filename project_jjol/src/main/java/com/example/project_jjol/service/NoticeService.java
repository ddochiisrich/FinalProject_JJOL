package com.example.project_jjol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_jjol.model.Notice;
import com.example.project_jjol.repository.NoticeMapper;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> findAll() {
        return noticeMapper.findAll();
    }

    public Notice findById(Long id) {
        return noticeMapper.findById(id);
    }

    public void save(Notice notice) {
        noticeMapper.save(notice);
    }

    public void update(Notice notice) {
        noticeMapper.update(notice);
    }

    public void delete(Long id) {
        noticeMapper.delete(id);
    }
    
    
}
