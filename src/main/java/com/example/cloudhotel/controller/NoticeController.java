package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Notice;
import com.example.cloudhotel.service.NoticeService;
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
 * fileName : NoticeController
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
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    //    1) 전체조회 + like 검색 함수 : 전체조회페이지
//     1)전체 조회 함수 + 2) 부서명 like 검색 함수(페이징처리)
    @GetMapping("/notice")
    public ResponseEntity<Object> getNoticeAll(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Notice> noticePage
                    = noticeService.findAllByTitleContaining(title, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("notice", noticePage.getContent()); // notice객체배열
            response.put("currentPage", noticePage.getNumber()); // 현재페이지번호
            response.put("totalItems", noticePage.getTotalElements()); // 총개수(건수)
            response.put("totalPages", noticePage.getTotalPages()); // 총페이지수

            if (noticePage.isEmpty() == false) {
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


    //    새로운 Notice 저장 함수 : 리액트의 추가페이지
    @PostMapping("/notice")
    public ResponseEntity<Object> createNotice(
            @RequestBody Notice notice
    ) {
        try {
            // 저장 서비스 함수 호출
            Notice notice2 = noticeService.save(notice);

            return new ResponseEntity<>(notice2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   Notice 수정 함수 : 리액트의 상세페이지(no, Notice 객체)
    @PutMapping("/notice/{no}")
    public ResponseEntity<Object> updateNotice(
            @PathVariable int no,
            @RequestBody Notice notice
    ) {
        try {
            // 저장 서비스 함수 호출(수정)
            Notice notice2 = noticeService.save(notice);

            return new ResponseEntity<>(notice2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   Notice 상세조회 함수 : 리액트의 상세페이지(no)
    @GetMapping("/notice/{no}")
    public ResponseEntity<Object> getNoticeId(@PathVariable int no) {
        try {
            // 저장 서비스 함수 호출(수정)
            Optional<Notice> optionalNotice
                    = noticeService.findById(no);

            if(optionalNotice.isPresent() == true) {
//                성공( optionalNotice.get() : 객체 꺼내기 함수 )
                return new ResponseEntity<>(optionalNotice.get(), HttpStatus.OK);
            } else {
//                데이터 없음(NO_CONTENT:204)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   Notice 삭제 함수 : 리액트의 상세페이지(no)
    @DeleteMapping("/notice/deletion/{no}")
    public ResponseEntity<Object> deleteNotice(
            @PathVariable int no
    ) {
        try {
            // 삭제 서비스 함수 호출
            boolean bSuccess = noticeService.removeById(no);

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
