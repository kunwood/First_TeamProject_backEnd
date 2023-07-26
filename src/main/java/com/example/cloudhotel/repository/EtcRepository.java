package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Etc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : EtcRepository
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
@Repository
public interface EtcRepository extends JpaRepository<Etc,Integer> {

    //    count 컬럼 데이터 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_ETC " +
            "SET COUNT = :count " +
            "WHERE ENO = :eno", nativeQuery = true)
    int selectUpdateCount(@Param("count") int count,
                      @Param("eno") int eno);

    //  MUL_EPRICE 데이터 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_ETC " +
            "SET MUL_EPRICE = (select eprice*:count from tb_etc where eno = :eno) " +
            "WHERE ENO = :eno",nativeQuery = true)
    int selectUpdateMulEprice(@Param("count") int count,
                              @Param("eno") int eno);

//    MUL_EPRICE 데이터 합계 구하는 함수
    @Query(value = "select sum(mul_eprice) from tb_etc",nativeQuery = true)
    int selectSumMulEprice();

//    MUL_EPRICE 데이터 0으로 변경하는 함수
//    @Query(value = "UPDATE TB_ETC " +
//            "SET mul_eprice = 0",nativeQuery = true)
//    void selectMulEpriceZero();

}
