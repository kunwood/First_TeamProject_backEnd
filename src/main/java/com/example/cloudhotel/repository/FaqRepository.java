package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : FaqRepository
 * author : 605
 * date : 2023-06-15
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-15         605          최초 생성
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq,Integer> {
}
