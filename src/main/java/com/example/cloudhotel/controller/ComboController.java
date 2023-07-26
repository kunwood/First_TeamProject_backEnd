package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Combo;
import com.example.cloudhotel.model.Member;
import com.example.cloudhotel.service.ComboService;
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

/**
 * comboName : com.example.cloudhotel.controller
 * fileName : PackageController
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
public class ComboController {
    @Autowired
    ComboService comboService;

    //   날짜로 조회하기 + 페이징처리
    @GetMapping("/combo/checkIn/checkOut/paging")
    public ResponseEntity<Object> selectByDate(@RequestParam(required = false, defaultValue = "")  String checkIn,
                                               @RequestParam(required = false, defaultValue = "")  String checkOut,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Combo> combos =
                    comboService.selectByDate(checkIn, checkOut, pageable);

            Map<String, Object> response = new HashMap<>();

//            Map 자료구조에 값 넣기 : put(키, 값);
            response.put("combo", combos.getContent()); // 룸 객체
            response.put("currentPage", combos.getNumber()); // 현재 페이지
            response.put("totalItems", combos.getTotalElements()); // 총 개수
            response.put("totalPages", combos.getTotalPages()); // 총 페이지

            if (combos.isEmpty() == false){
                return  new ResponseEntity<>(response,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   이름/테마로 like 조회하기 + 페이징처리
    @GetMapping("/combo/pname/theme/paging")
    public ResponseEntity<Object> selectBypname(@RequestParam(required = false, defaultValue = "")  String pname,
                                                @RequestParam(required = false, defaultValue = "")  String theme,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Combo> combos =
                    comboService.selectBypname(pname,theme, pageable);

            Map<String, Object> response = new HashMap<>();

//            Map 자료구조에 값 넣기 : put(키, 값);
            response.put("combo", combos.getContent()); // 룸 객체
            response.put("currentPage", combos.getNumber()); // 현재 페이지
            response.put("totalItems", combos.getTotalElements()); // 총 개수
            response.put("totalPages", combos.getTotalPages()); // 총 페이지

            if (combos.isEmpty() == false){
                return  new ResponseEntity<>(response,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    패키지 번호로 삭제 함수
    @DeleteMapping("/combo/deletion/{pno}")
    public ResponseEntity<Object> deletePackage(@PathVariable int pno){
        try {
//        삭제 서비스 함수 호출
            boolean bSuccess = comboService.removeById(pno);
            if (bSuccess == true){
//            삭제 성공
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    새로운 패키지정보 저장 함수
    @PostMapping("/combo")
    public ResponseEntity<Object> createcombo(@RequestBody Combo combo){
        try {
//            저장 서비스 함수 호출
            Combo combo2 = comboService.save(combo);
            return new ResponseEntity<>(combo2, HttpStatus.OK);

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   패키지 수정 함수
    @PutMapping("/combo/{pno}")
    public ResponseEntity<Object> updateCombo(
            @PathVariable int pno,
            @RequestBody Combo combo
    ) {
        try {
            // 저장 서비스 함수 호출(수정)
            Combo combo2 = comboService.save(combo);

            return new ResponseEntity<>(combo2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  패키지 + 추가사항 가격 합계 함수
    @GetMapping("/combo/totalPrice")
    public ResponseEntity<Object> selectTotalPrice(@RequestParam(required = false, defaultValue = "") String pname){
        try {
            int price = comboService.selectSumCombo(pname);
            return new ResponseEntity<>(price, HttpStatus.OK);

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
