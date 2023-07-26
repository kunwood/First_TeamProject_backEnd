package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : NoticeRepository
 * author : 605
 * date : 2023-06-21
 * description : JPA CRUD 위한 인터페이스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
//    title(제목) like 검색
    Page<Notice> findAllByTitleContainingOrderByInsertTimeDesc(String title, Pageable pageable);

}
