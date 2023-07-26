package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Notice;
import com.example.cloudhotel.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : NoticeService
 * author : 605
 * date : 2023-06-21
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;


    //    전체조회함수
    public Page<Notice> findAll(Pageable pageable){
        Page<Notice> page = noticeRepository.findAll(pageable);
        return page;
    }

    //    제목(title) like 검색 : 전체조회페이지
    public Page<Notice> findAllByTitleContaining(String title, Pageable pageable) {
        Page<Notice> page
                = noticeRepository
                .findAllByTitleContainingOrderByInsertTimeDesc(title,pageable);

        return page;
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public Notice save(Notice notice) {
        Notice notice2 = noticeRepository.save(notice);

        return notice2;
    }

    //   기본키로 상세 조회(1건조회) 함수 : 리액트 상세 페이지
    public Optional<Notice> findById(int no) {
        Optional<Notice> optionalNotice
                = noticeRepository.findById(no);

        return optionalNotice;
    }


    //   번호(no) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(int no) {
        if(noticeRepository.existsById(no) == true) {
//            삭제 실행
            noticeRepository.deleteById(no);
            return true;
        } else {
            return false;
        }
    }


}
