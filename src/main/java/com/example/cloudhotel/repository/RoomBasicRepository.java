package com.example.cloudhotel.repository;

import com.example.cloudhotel.dto.RoomDto;
import com.example.cloudhotel.model.RoomBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : RoomBasicRepository
 * author : 605
 * date : 2023-06-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-29         605          최초 생성
 */
@Repository
public interface RoomBasicRepository extends JpaRepository<RoomBasic,Integer> {

//    타입코드로 조회하기
    RoomBasic findByRtype(String rtype);

//   총 가격(룸  + 추가항목)구하기
    @Query(value = "select rprice*(SELECT TO_DATE(:checkOut,'YYYY-MM-DD') - TO_DATE(:checkIn,'YYYY-MM-DD') AS DIFF FROM DUAL) + (select sum(mul_eprice) from tb_etc) as total_price  " +
            "from tb_room_basic " +
            "where RTYPE = :rtype",nativeQuery = true)
    int selectTotalPrice(@Param("checkOut") String checkOut,
                         @Param("checkIn") String checkIn,
                         @Param("rtype") String rtype);

    //    룸 베이직 Like 테이블 조회, rtype 나오도록
    @Query(value = "SELECT rtype FROM TB_ROOM_BASIC " +
            "WHERE RTYPE like %:rtype%",nativeQuery = true)
    ArrayList<String> selectOnlyRtype(@Param("rtype") String rtype);

    //    룸 베이직 테이블 Like 조회
    @Query(value = "SELECT * FROM TB_ROOM_BASIC " +
            "WHERE RTYPE like %:rtype% ",nativeQuery = true)
    List<RoomDto> selectLikeRtype(@Param("rtype") String rtype);

//    룸 베이직 방 조회 리스트
@Query(value = "SELECT * FROM TB_ROOM_BASIC " +
        "WHERE RTYPE = :rtype",nativeQuery = true)
List<RoomDto> selectRtypeList(@Param("rtype") String rtype);

}
