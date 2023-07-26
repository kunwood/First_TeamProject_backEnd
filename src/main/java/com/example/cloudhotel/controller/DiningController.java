package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Dining;
import com.example.cloudhotel.service.DiningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : DiningController
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
@RestController
@RequestMapping("/api")
@Slf4j
public class DiningController {
    @Autowired
    DiningService diningService;

//    전체 조회
    @GetMapping("/dining")
    public ResponseEntity<Object> getDiningAll() {
//        에러 처리 : try ~ catch
        try {
//            1) 전체 조회 서비스 함수
            List<Dining> list
                    = diningService.findAll();

//          전체 조회 결과가 있으면 데이터 + 성공메세지 전송(출력)
            if(list.isEmpty() == false) {
//                데이터 + 성공메세지 출력(전송)
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
//                데이터 없음 , 데이터없음 메세지 출력(전송)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage()); // 에러발생시 로그 출력
//          서버 에러메세지 전송 (콘솔 출력)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
