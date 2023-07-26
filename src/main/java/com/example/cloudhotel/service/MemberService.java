package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Member;
import com.example.cloudhotel.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : MemberService
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
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    //    전체조회함수
    public Page<Member> findAll(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    //    email like 검색 : 전체조회페이지
    public Page<Member> findAllByEmailContaining(String email, Pageable pageable) {
        Page<Member> page
                = memberRepository
                .findAllByEmailContaining(email, pageable);

        return page;
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public Member save(Member member) {
        Member member2 = memberRepository.save(member);

        return member2;
    }

    //   기본키로 상세 조회(1건조회) 함수 : 리액트 상세 페이지
    public Optional<Member> findById(int mno) {
        Optional<Member> optionalMember
                = memberRepository.findById(mno);

        return optionalMember;
    }

    //   멤버id(mno) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(int mno) {
        if(memberRepository.existsById(mno) == true) {
//            삭제 실행
            memberRepository.deleteById(mno);
            return true;
        } else {
            return false;
        }
    }


}
