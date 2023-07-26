package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Etc;
import com.example.cloudhotel.service.EtcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : EtcController
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
@Slf4j
@RestController
@RequestMapping("/api")
public class EtcController {
    @Autowired
    EtcService etcService;

//    전체 조회 함수
    @GetMapping("/etc")
    public ResponseEntity<Object> getEtcAll() {
//        에러 처리 : try ~ catch
        try {
//            1) 전체 조회 서비스 함수
            List<Etc> list
                    = etcService.findAll();

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

    //    count 컬럼 데이터 업데이트
    @PutMapping("/etc/update/count")
    public ResponseEntity<Object> addRest(@RequestParam(required = false, defaultValue = "") int count,
                                          @RequestParam(required = false, defaultValue = "") int eno){
        try {
            int count2 = etcService.selectUpdateCount(count, eno);
            return new ResponseEntity<>(count2, HttpStatus.OK);

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  TODO   count + MulEprice 컬럼 데이터 업데이트
    @PutMapping("/etc/update/count/mulEprice")
    public ResponseEntity<Object> updateCountMulEprice(@RequestParam(required = false, defaultValue = "") int count,
                                          @RequestParam(required = false, defaultValue = "") int eno){
        try {
            int count2 = etcService.selectUpdateCount(count, eno);
            return new ResponseEntity<>(count2, HttpStatus.OK);

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    예약완료시 MulEprice 컬럼 0으로 값 변경
//    @PutMapping("/etc/update/mulEprice/zero")
//    public ResponseEntity<Object> selectMulEpriceZero(){
//        try {
//         etcService.selectMulEpriceZero();
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        }catch (Exception e){
//            log.debug(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
