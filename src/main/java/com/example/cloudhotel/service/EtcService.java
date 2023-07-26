package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Etc;
import com.example.cloudhotel.repository.EtcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : EtcService
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
@Service
public class EtcService {
    @Autowired
    EtcRepository etcRepository;

    //    전체조회함수
    public List<Etc> findAll(){
        List<Etc> list = etcRepository.findAll();
        return list;
    }

    //    count + MulEprice  컬럼 데이터 업데이트
    public int selectUpdateCount(int count,
                                 int eno){
        int count2 = etcRepository.selectUpdateCount(count, eno);
        etcRepository.selectUpdateMulEprice(count, eno);
        return count2;
    }

//    예약완료시 MulEprice 컬럼 0으로 값 변경
//    public void selectMulEpriceZero(){
//      etcRepository.selectMulEpriceZero();
//
//    }

}
