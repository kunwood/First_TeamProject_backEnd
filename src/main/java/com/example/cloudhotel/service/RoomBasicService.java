package com.example.cloudhotel.service;

import com.example.cloudhotel.model.RoomBasic;
import com.example.cloudhotel.repository.RoomBasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : RoomBasicService
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
@Service
public class RoomBasicService {
    @Autowired
    RoomBasicRepository roomBasicRepository;

    public RoomBasic findByRtype(String rtype){
      RoomBasic roomBasic = roomBasicRepository.findByRtype(rtype);
        return roomBasic;
    }
}
