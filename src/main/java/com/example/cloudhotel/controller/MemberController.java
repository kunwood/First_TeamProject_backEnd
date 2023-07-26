package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Member;
import com.example.cloudhotel.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : MemberController
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

@Slf4j
@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    MemberService memberService;

    //    1) 전체조회 + like 검색 함수 : 전체조회페이지
//     1)전체 조회 함수 + 2) 부서명 like 검색 함수(페이징처리)
    @GetMapping("/member")
    public ResponseEntity<Object> getMemberAll(
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Member> memberPage
                    = memberService.findAllByEmailContaining(email, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("member", memberPage.getContent()); // member객체배열
            response.put("currentPage", memberPage.getNumber()); // 현재페이지번호
            response.put("totalItems", memberPage.getTotalElements()); // 총개수(건수)
            response.put("totalPages", memberPage.getTotalPages()); // 총페이지수

            if (memberPage.isEmpty() == false) {
                // 성공
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage()); // 에러 출력
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    새로운 멤버 저장 함수 : 리액트의 추가페이지
    @PostMapping("/member")
    public ResponseEntity<Object> createMember(
            @RequestBody Member member
    ) {
        try {
            // 저장 서비스 함수 호출
            Member member2 = memberService.save(member);

            return new ResponseEntity<>(member2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //   멤버 수정 함수 : 리액트의 상세페이지(mno, 멤버객체)
    @PutMapping("/member/{mno}")
    public ResponseEntity<Object> updateMember(
            @PathVariable int mno,
            @RequestBody Member member
    ) {
        try {
            // 저장 서비스 함수 호출(수정)
            Member member2 = memberService.save(member);

            return new ResponseEntity<>(member2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   멤버 상세조회 함수 : 리액트의 상세페이지(mno)
    @GetMapping("/member/{mno}")
    public ResponseEntity<Object> getMemberId(@PathVariable int mno) {
        try {
            // 저장 서비스 함수 호출(수정)
            Optional<Member> optionalMember
                    = memberService.findById(mno);

            if(optionalMember.isPresent() == true) {
//                성공( optionalMember.get() : 객체 꺼내기 함수 )
                return new ResponseEntity<>(optionalMember.get(), HttpStatus.OK);
            } else {
//                데이터 없음(NO_CONTENT:204)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   멤버 삭제 함수 : 리액트의 상세페이지(mno)
    @DeleteMapping("/member/deletion/{mno}")
    public ResponseEntity<Object> deleteMember(
            @PathVariable int mno
    ) {
        try {
            // 삭제 서비스 함수 호출
            boolean bSuccess = memberService.removeById(mno);

            if(bSuccess == true) {
                // 삭제 성공
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // 삭제할 데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
