package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : MemberRepository
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
public interface MemberRepository extends JpaRepository<Member, Integer> {
    //    email like 검색
    Page<Member> findAllByEmailContaining(String email, Pageable pageable);

//    member에 phone 번호 존재확인
    boolean existsByPhone(String phone);
}
