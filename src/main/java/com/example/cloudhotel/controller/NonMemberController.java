package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.NonMember;
import com.example.cloudhotel.service.NonMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : NonMemberController
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
public class NonMemberController {

    @Autowired
    NonMemberService nonMemberService;

    //    1) 전체조회 + like 검색 함수 : 전체조회페이지
//     1)전체 조회 함수 + 2) 부서명 like 검색 함수(페이징처리)
    @GetMapping("/nonmember")
    public ResponseEntity<Object> getNonMemberAll(
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<NonMember> nonMemberPage
                    = nonMemberService.findAllByEmailContaining(email, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("nonMember", nonMemberPage.getContent()); // nonMember객체배열
            response.put("currentPage", nonMemberPage.getNumber()); // 현재페이지번호
            response.put("totalItems", nonMemberPage.getTotalElements()); // 총개수(건수)
            response.put("totalPages", nonMemberPage.getTotalPages()); // 총페이지수

            if (nonMemberPage.isEmpty() == false) {
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
    @PostMapping("/nonmember")
    public ResponseEntity<Object> createNonMember(
            @RequestBody NonMember nonMember
    ) {
        try {
            // 저장 서비스 함수 호출
            NonMember nonMember2 = nonMemberService.save(nonMember);

            return new ResponseEntity<>(nonMember2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //   멤버 수정 함수 : 리액트의 상세페이지(nno, 멤버객체)
    @PutMapping("/nonmember/{nno}")
    public ResponseEntity<Object> updateNonMember(
            @PathVariable int nno,
            @RequestBody NonMember nonMember
    ) {
        try {
            // 저장 서비스 함수 호출(수정)
            NonMember nonMember2 = nonMemberService.save(nonMember);

            return new ResponseEntity<>(nonMember2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   멤버 상세조회 함수 : 리액트의 상세페이지(nno)
    @GetMapping("/nonmember/{nno}")
    public ResponseEntity<Object> getNonMemberId(@PathVariable int nno) {
        try {
            // 저장 서비스 함수 호출(수정)
            Optional<NonMember> optionalNonMember
                    = nonMemberService.findById(nno);

            if(optionalNonMember.isPresent() == true) {
//                성공( optionalNonMember.get() : 객체 꺼내기 함수 )
                return new ResponseEntity<>(optionalNonMember.get(), HttpStatus.OK);
            } else {
//                데이터 없음(NO_CONTENT:204)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   멤버 삭제 함수 : 리액트의 상세페이지(nno)
    @DeleteMapping("/nonmember/deletion/{nno}")
    public ResponseEntity<Object> deleteNonMember(
            @PathVariable int nno
    ) {
        try {
            // 삭제 서비스 함수 호출
            boolean bSuccess = nonMemberService.removeById(nno);

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
