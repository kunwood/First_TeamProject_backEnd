package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Member;
import com.example.cloudhotel.model.Reservation;
import com.example.cloudhotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * packageName : com.example.cloudhotel.service
 * fileName : ReservationService
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
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    //    전체조회함수
    public Page<Reservation> findAll(Pageable pageable){
        Page<Reservation> page = reservationRepository.findAll(pageable);
        return page;
    }

    //    이메일(email) like 검색 : 전체조회페이지
    public Page<Reservation> findAllByEmailContaining(String email, Pageable pageable) {
        Page<Reservation> page
                = reservationRepository
                .findAllByEmailContaining(email,pageable);

        return page;
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public Reservation save(Reservation reservation) {
        Reservation reservation2 = reservationRepository.save(reservation);

        return reservation2;
    }

    //   기본키로 상세 조회(1건조회) 함수 : 리액트 상세 페이지
    public Optional<Reservation> findById(int rvno) {
        Optional<Reservation> optionalReservation
                = reservationRepository.findById(rvno);

        return optionalReservation;
    }

    //   예약번호(rvno) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(int rvno) {
        if(reservationRepository.existsById(rvno) == true) {
//            삭제 실행
            reservationRepository.deleteById(rvno);
            return true;
        } else {
            return false;
        }
    }


}
