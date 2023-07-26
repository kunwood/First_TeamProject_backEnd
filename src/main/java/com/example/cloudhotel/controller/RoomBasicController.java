package com.example.cloudhotel.controller;

import com.example.cloudhotel.dto.RoomDto;
import com.example.cloudhotel.model.RoomBasic;
import com.example.cloudhotel.service.RoomBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : RoomBasicController
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
@RestController
@RequestMapping("/api")
@Slf4j
public class RoomBasicController {
    @Autowired
    RoomBasicService roomBasicService;

    //  룸 타입으로 조회
    @GetMapping("/reserve")
    public ResponseEntity<Object> searchRoom(@RequestParam("rtype") String rtype) {

        try {
            RoomBasic roomBasic = roomBasicService.findByRtype(rtype);
            return new ResponseEntity<>(roomBasic, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
