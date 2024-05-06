package com.HAPPYTRIP.service;

import com.HAPPYTRIP.domain.Notice;
import com.HAPPYTRIP.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    //조회
    public List<Notice> getList() {
        return noticeRepository.findAll();
    }

    public Page<Notice> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page, 10);
        return noticeRepository.findAllByKeyword(kw, pageable);
    }
    public Notice getNotice(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        } else {
            throw new RuntimeException("Notice not found");
        }
    }

    //추가
    public void create(String title, String content) {
        Notice n = new Notice();
        n.setTitle(title);
        n.setContent(content);
        this.noticeRepository.save(n);
    }

    //수정
    public void update(Long id, String title, String content) {
        Optional<Notice> optionalNotice = noticeRepository.findById(id);
        if (optionalNotice.isPresent()) {
            Notice n = optionalNotice.get();
            n.setTitle(title);
            n.setContent(content);
            this.noticeRepository.save(n);
        } else {
            throw new EntityNotFoundException("공지를 찾을 수 없습니다.");
        }
    }

    //삭제
    public void delete(Notice notice) {
        noticeRepository.delete(notice);
    }

    public void deleteById(Long id) {
        noticeRepository.deleteById(id);
    }
}