package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * packageName : com.example.cloudhotel.repository
 * fileName : PackageRepository
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
public interface ComboRepository extends JpaRepository<Combo,Integer> {
    
//    기간 검색
    @Query(value = "SELECT * from tb_Combo " +
            "WHERE check_in <= :checkIn  " +
            "and check_out >= :checkOut " +
            "and DELETE_YN = 'N'", nativeQuery = true)
    Page<Combo> selectByDate(@Param("checkIn") String checkIn,
                             @Param("checkOut") String checkOut,
                             Pageable pageable);

//    제목/키워드 like 검색
    @Query(value = "select * from tb_combo " +
            "where pname like %:pname% " +
            "AND theme LIKE %:theme%", nativeQuery = true)
    Page<Combo> selectBypname(@Param("pname") String pname,
                              @Param("theme") String theme,
                                Pageable pageable);

//    패키지 + 추가사항 가격 합계 함수
    @Query(value = "select pprice + (select sum(mul_eprice) from tb_etc) as total_price  " +
            "from tb_combo " +
            "where pname = :pname",nativeQuery = true)
    int selectSumCombo(@Param("pname") String pname);
    
}
