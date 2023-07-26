package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Reservation;
import com.example.cloudhotel.repository.MemberRepository;
import com.example.cloudhotel.repository.ReservationRepository;
import com.example.cloudhotel.repository.UserRepository;
import com.example.cloudhotel.service.ReservationService;
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
 * fileName : ReservationController
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
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;



    //    1) 전체조회 + like 검색 함수 : 전체조회페이지
//     1)전체 조회 함수 + 2) email like 검색 함수(페이징처리)
    @GetMapping("/reservation")
    public ResponseEntity<Object> getReservationAll(
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Reservation> reservationPage
                    = reservationService.findAllByEmailContaining(email, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("reservation", reservationPage.getContent()); // reservation객체배열
            response.put("currentPage", reservationPage.getNumber()); // 현재페이지번호
            response.put("totalItems", reservationPage.getTotalElements()); // 총개수(건수)
            response.put("totalPages", reservationPage.getTotalPages()); // 총페이지수

            if (reservationPage.isEmpty() == false) {
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

    //  저장 함수
    @PostMapping("/reservation")
    public ResponseEntity<Object> createReservation(
            @RequestBody Reservation reservation
    ) {
        try {
            // 멤버십 여부 조회 및 설정
            String phone = reservation.getPhone();
            boolean hasMembership = userRepository.existsByPhone(phone);
            reservation.setMembership(hasMembership ? "Y" : "N");

            // 저장 서비스 함수 호출
            Reservation reservation2 = reservationService.save(reservation);

            return new ResponseEntity<>(reservation2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //   예약 수정 함수
    @PutMapping("/reservation/{rvno}")
    public ResponseEntity<Object> updateReservation(
            @PathVariable int rvno,
            @RequestBody Reservation reservation
    ) {
        try {
            // 멤버십 여부 조회 및 설정
            String phone = reservation.getPhone();
            boolean hasMembership = userRepository.existsByPhone(phone);
            reservation.setMembership(hasMembership ? "Y" : "N");

            // 저장 서비스 함수 호출(수정)
            Reservation reservation2 = reservationService.save(reservation);

            return new ResponseEntity<>(reservation2, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   예약 상세조회 함수
    @GetMapping("/reservation/{rvno}")
    public ResponseEntity<Object> getReservationId(@PathVariable int rvno) {
        try {
            // 저장 서비스 함수 호출(수정)
            Optional<Reservation> optionalReservation
                    = reservationService.findById(rvno);

            if(optionalReservation.isPresent() == true) {
//                성공( optionalReservation.get() : 객체 꺼내기 함수 )
                return new ResponseEntity<>(optionalReservation.get(), HttpStatus.OK);
            } else {
//                데이터 없음(NO_CONTENT:204)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   예약 삭제 함수
    @DeleteMapping("/reservation/deletion/{rvno}")
    public ResponseEntity<Object> deleteReservation(
            @PathVariable int rvno
    ) {
        try {
            // 삭제 서비스 함수 호출
            boolean bSuccess = reservationService.removeById(rvno);

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
