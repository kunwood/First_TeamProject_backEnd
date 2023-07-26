package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Facilities;
import com.example.cloudhotel.repository.FacilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : FacilitiesService
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
public class FacilitiesService {
    @Autowired
    FacilitiesRepository facilitiesRepository;

    //    전체조회함수
    public List<Facilities> findAll(){
        List<Facilities> list = facilitiesRepository.findAll();
        return list;
    }
}
