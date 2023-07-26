package com.example.cloudhotel.repository;

import com.example.cloudhotel.dto.RoomDto;
import com.example.cloudhotel.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : RoomRepository
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
@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {


//  TODO :   rest 컬럼 데이터 업데이트
@Transactional
@Modifying
@Query(value = "UPDATE TB_ROOM  " +
        "SET rest=((SELECT rest from TB_ROOM where RTYPE = :rtype " +
        "and   check_IN = :checkIn) -1) " +
        "where RTYPE = :rtype " +
        "and   check_IN = :checkIn ", nativeQuery = true)
int selectAddRest(@Param("checkIn") String checkIn,
                  @Param("rtype") String rtype);

// TODO : 해당 날짜에 방데이터 있는지(개수) 조회
    @Query(value = "select count(*) from tb_room " +
            "where check_in = :checkIn " +
            "and RTYPE = :rtype "  ,nativeQuery = true)
    int selectsearchRoom(@Param("checkIn") String checkIn,
                         @Param("rtype") String rtype);

//    쿼리스트링 (IN 이 내부 명령어..?)
//    int countByCheckInAndRtypeAndRestGreaterThanEqual(String checkIn,String rtype,int restnum);



    //   예약완료시 룸테이블에 insert 쿼리
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TB_ROOM(RNO,RTYPE,RPRICE,CHECK_IN,TOTAL,REST,PEOPLE,ROOMURL,INSERT_TIME) " +
            "VALUES (SQ_ROOM.NEXTVAL, :#{#room.rtype}, " +
            ":#{#room.rprice}, " +
            ":#{#room.checkIn}, " +
            ":#{#room.total}, " +
            ":#{#room.rest}, " +
            ":#{#room.people}, " +
            ":#{#room.roomurl}, "+
            "TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ) "
            , nativeQuery = true
    )
    int insertByRoom(@Param("room") Room room);

//  체크인/아웃 기간 내 일자 구하는 함수
    @Query(value = "SELECT TO_CHAR(TO_DATE(:checkIn,'YYYY-MM-DD') + (LEVEL-1), 'YYYY-MM-DD') AS \"DATE\" " +
            "  FROM DUAL\n" +
            "CONNECT BY LEVEL <= TO_DATE(:checkOut,'YYYY-MM-DD') - TO_DATE(:checkIn,'YYYY-MM-DD')",nativeQuery = true)
    ArrayList<String> selectArray(@Param("checkIn") String checkIn,
                                  @Param("checkOut") String checkOut);


//    countCheckDays 연박 확인하기
    @Query(value = "SELECT COUNT(*)-1 FROM TB_ROOM " +
            "WHERE CHECK_IN BETWEEN :checkIn AND (TO_CHAR(TO_DATE(:checkOut) - 1,'YYYY-MM-DD')) "
            , nativeQuery = true)
            int countCheckDays(@Param("checkIn") String checkIn,
                               @Param("checkOut") String checkOut);


    //  countUnavailableRooms 룸테이블의 남은방(REST)=0 의 데이터 몇개인지 갯수세기
    @Query(value = "SELECT COUNT(*) FROM TB_ROOM " +
            "WHERE CHECK_IN BETWEEN :checkIn AND (TO_CHAR(TO_DATE(:checkOut) - 1, 'YYYY-MM-DD')) " +
            "AND REST = 0 " +
            "AND RTYPE = :rtype ", nativeQuery = true)
    int countUnavailableRooms(@Param("checkIn") String checkIn,
                              @Param("checkOut") String checkOut,
                              @Param("rtype") String rtype);

// 룸타입+체크인으로 예약불가인 방 개수 구하기
    @Query(value = "select count(*) from tb_room " +
            "WHERE rtype = :rtype " +
            "and check_in = :checkIn " +
            "and rest = 0",nativeQuery = true)
    int selectNumUnableRoom(@Param("rtype") String rtype,
                            @Param("checkIn") String checkIn);

//    룸에서 rtype like 조회
    @Query(value = "select * from tb_room " +
            "where rtype like %:rtype% " +
            "and check_in = :checkIn",nativeQuery = true)
    List<RoomDto> selectLikeRoom(@Param("checkIn") String checkIn,@Param("rtype") String rtype);

// 선택된 일자에 남은방이 0인 데이터 조회 : NEW!!!
    @Query(value = "SELECT distinct rtype,rprice,rest,roomUrl FROM (select * from TB_ROOM order by check_in) " +
            "WHERE CHECK_IN BETWEEN :checkIn AND (TO_CHAR(TO_DATE(:checkOut) - 1,'YYYY-MM-DD')) " +
            "and REST = 0 " +
            "AND RTYPE like %:rtype% " +
            "AND ROWNUM<=4", nativeQuery = true)
    List<RoomDto> unavailableRooms(@Param("checkIn") String checkIn,
                                   @Param("checkOut") String checkOut,
                                   @Param("rtype") String rtype);

    //  TODO :  예약취소시 rest 컬럼 데이터 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_ROOM  " +
            "SET rest=((SELECT rest from TB_ROOM where RTYPE = :rtype " +
            "and   check_IN = :checkIn) +1) " +
            "where RTYPE = :rtype " +
            "and   check_IN = :checkIn ", nativeQuery = true)
    int selectPlusRest(@Param("checkIn") String checkIn,
                      @Param("rtype") String rtype);

}
