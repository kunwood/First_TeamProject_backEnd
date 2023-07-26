package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : ReservationRepository
 * author : 605
 * date : 2023-06-21
 * description : JPA CRUD 위한 인터페이스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    //    email 조회하는 like 검색 함수
    Page<Reservation> findAllByEmailContaining(String email, Pageable pageable);

//    특정날짜에 특정 룸 예약 개수 몇개인지 세기 : 남은방 개수 셀 때 사용하려고했으나 현재는 해당 함수 수정되어 사용 안 함
    @Query(value = "SELECT count(*) FROM TB_RESERVATION " +
            "where rtype = :rtype " +
            "and   DELETE_YN = 'N' " +
            "and   check_in = :checkIn",nativeQuery = true)
    int rsvNum(@Param("rtype") String rtype,
               @Param("checkIn") String checkIn);

}
