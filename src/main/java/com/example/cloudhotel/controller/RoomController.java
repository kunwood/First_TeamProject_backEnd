package com.example.cloudhotel.controller;

import com.example.cloudhotel.dto.RoomDto;
import com.example.cloudhotel.model.Room;
import com.example.cloudhotel.model.Room;
import com.example.cloudhotel.service.ReservationService;
import com.example.cloudhotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName : com.example.cloudhotel.controller
 * fileName : RoomController
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
@RestController
@RequestMapping("/api")
@Slf4j
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    ReservationService reservationService;


    //    전체 조회 함수
    @GetMapping("/room")
    public ResponseEntity<Object> getRoomAll() {
//        에러 처리 : try ~ catch
        try {
//            1) 전체 조회 서비스 함수
            List<Room> list
                    = roomService.findAll();

//          전체 조회 결과가 있으면 데이터 + 성공메세지 전송(출력)
            if(list.isEmpty() == false) {
//                데이터 + 성공메세지 출력(전송)
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
//                데이터 없음 , 데이터없음 메세지 출력(전송)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage()); // 에러발생시 로그 출력
//          서버 에러메세지 전송 (콘솔 출력)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //  TODO : 총 가격 계산(룸 + 조식)
    @GetMapping("/room/totalPrice")
    public ResponseEntity<Object> selectTotalPrice(@RequestParam(required = false, defaultValue = "") String checkOut,
                                                   @RequestParam(required = false, defaultValue = "") String checkIn,
                                                   @RequestParam(required = false, defaultValue = "") String rtype){
        try {
            int price = roomService.selecttotalPrice(checkOut,checkIn,rtype);
            return new ResponseEntity<>(price, HttpStatus.OK);

        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // TODO:  ((남은방 업데이트함수 최종))  예약완료시 REST 데이터 갱신 (룸 테이블에 데이터 있을시에만 갱신)
    @PutMapping("/room/update/array/rest")
    public ResponseEntity<Object> updateRest(@RequestParam(required = false, defaultValue = "") String checkIn,
                                          @RequestParam(required = false, defaultValue = "") String rtype,
                                            @RequestParam(required = false, defaultValue = "") String checkOut){

        try {
            roomService.updateRest(checkIn, rtype,checkOut);
                return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //  TODO  : 룸테이블 저장 함수 : 예약완료시 실행하기 (룸테이블에 데이터 없을시에만 저장), 체크인/아웃,룸이름 객체형태로 넘겨주기!
    @PostMapping("/room/save/array")
    public ResponseEntity<Object> saveRoom(@RequestBody Room room
    ) {

        try {

            // 저장 서비스 함수 호출

                roomService.saveRoom(room);
                return new ResponseEntity<>(HttpStatus.OK);


        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//  TODO :  조회 함수 최종
@GetMapping("/booking")
public ResponseEntity<Object> searchRoom(@RequestParam("checkIn") String checkIn,
                                             @RequestParam("checkOut") String checkOut,
                                             @RequestParam("rtype") String rtype) {

    try {
        List<RoomDto> roomDto = roomService.searchRoomList(checkIn, checkOut, rtype);
        return new ResponseEntity<>(roomDto,HttpStatus.OK);

    } catch (Exception e) {
        log.debug(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    // TODO: 예약취소시 REST 데이터 갱신
    @PutMapping("/room/plus/array/rest")
    public ResponseEntity<Object> plusRest(@RequestParam(required = false, defaultValue = "") String checkIn,
                                             @RequestParam(required = false, defaultValue = "") String rtype,
                                             @RequestParam(required = false, defaultValue = "") String checkOut){

        try {
            roomService.plusRest(checkIn, rtype,checkOut);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}


