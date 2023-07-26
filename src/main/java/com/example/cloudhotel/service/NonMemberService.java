package com.example.cloudhotel.service;

import com.example.cloudhotel.model.NonMember;
import com.example.cloudhotel.repository.NonMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : NonMemberService
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
public class NonMemberService {

    @Autowired
    NonMemberRepository monMemberRepository;

    //    전체조회함수
    public Page<NonMember> findAll(Pageable pageable){
        Page<NonMember> page = monMemberRepository.findAll(pageable);
        return page;
    }

    //    email like 검색 : 전체조회페이지
    public Page<NonMember> findAllByEmailContaining(String email, Pageable pageable) {
        Page<NonMember> page
                = monMemberRepository
                .findAllByEmailContaining(email, pageable);

        return page;
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public NonMember save(NonMember monMember) {
        NonMember monMember2 = monMemberRepository.save(monMember);

        return monMember2;
    }

    //   기본키로 상세 조회(1건조회) 함수 : 리액트 상세 페이지
    public Optional<NonMember> findById(int nno) {
        Optional<NonMember> optionalNonMember
                = monMemberRepository.findById(nno);

        return optionalNonMember;
    }

    //   비회원id(nno) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(int nno) {
        if(monMemberRepository.existsById(nno) == true) {
//            삭제 실행
            monMemberRepository.deleteById(nno);
            return true;
        } else {
            return false;
        }
    }
}
