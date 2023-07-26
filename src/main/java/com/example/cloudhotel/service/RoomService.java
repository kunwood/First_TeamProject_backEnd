package com.example.cloudhotel.service;

import com.example.cloudhotel.dto.RoomDto;
import com.example.cloudhotel.model.Room;
import com.example.cloudhotel.model.RoomBasic;
import com.example.cloudhotel.repository.EtcRepository;
import com.example.cloudhotel.repository.ReservationRepository;
import com.example.cloudhotel.repository.RoomBasicRepository;
import com.example.cloudhotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : RoomService
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
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomBasicRepository roomBasicRepository;
    @Autowired
    ReservationRepository reservationRepository;

    //    전체조회함수
    public List<Room> findAll() {
        List<Room> list = roomRepository.findAll();
        return list;
    }



    //  TODO  총 가격 계산(룸 + 추가사항)
    public int selecttotalPrice(String checkOut, String checkIn, String rtype) {
        int price = roomBasicRepository.selectTotalPrice(checkOut, checkIn, rtype);
        return price;
    }

    //    TODO : 남은방 업데이트 함수 최종
    public void updateRest(String checkIn, String rtype, String checkOut) {

        ArrayList<String> al = roomRepository.selectArray(checkIn, checkOut);
        for (int i = 0; i < al.size(); i++) {
            //        룸 테이블에 해당날짜의 데이터가 있는지 조회
            int rsvNum = roomRepository.selectsearchRoom(al.get(i), rtype);
            if (rsvNum == 1) {
//            룸 테이블에 데이터 있으면 REST 레코드 업데이트
                int room = roomRepository.selectAddRest(al.get(i), rtype);

            } else {
                int room = 0;
            }
        }

    }

    // TODO  예약완료시 룸테이블에 insert 쿼리 최종
    public void saveRoom(Room room) {

//        체크인 정의
        ArrayList<String> al = roomRepository.selectArray(room.getCheckIn(), room.getCheckOut());
        for (int i = 0; i < al.size(); i++) {

            int roomNum = roomRepository.selectsearchRoom(al.get(i), room.getRtype());
            if (roomNum == 0) {
//        1) total 값 가져오기 : roomBasic.getTotal()
                RoomBasic roomBasic = roomBasicRepository.findByRtype(room.getRtype());
//        2) room 에 계산된 정보를 추가 저장(setter)
                room.setTotal(roomBasic.getTotal());
//        3) 남은방개수 : selectAddRest()
//        int restNum = roomRepository.selectAddRest(room.getCheckIn(), room.getRtype());
//        room.setRest(restNum);
                int totalNum = room.getTotal();
                int rsvNum2 = reservationRepository.rsvNum(room.getRtype(), al.get(i));
                int restNum3 = totalNum - rsvNum2;
                room.setRest(restNum3);
//       5) 가격 저장
                room.setRprice(roomBasic.getRprice());
//        6) 인원수 저장
                room.setPeople(roomBasic.getPeople());
//        7) 체크인 날짜 저장
                room.setCheckIn(al.get(i));
//        8) 링크 저장
                room.setRoomurl(roomBasic.getRoomurl());

                roomRepository.insertByRoom(room);
            }

        }


    }


    //    TODO 조회NEW : 최종
    public List<RoomDto> searchRoomList(String checkIn, String checkOut, String rtype) {
//        연박인지 확인하기
        int count = roomRepository.countCheckDays(checkIn, checkOut);

//       룸 베이직 Like 테이블 조회, 결과값 rtype 나오도록
        ArrayList<String> basicRtype = roomBasicRepository.selectOnlyRtype(rtype);

//    룸TB에서 예약불가인 날 있는지 개수 조회
        int NumCity = roomRepository.selectNumUnableRoom(basicRtype.get(0), checkIn);
        int NumOcean = roomRepository.selectNumUnableRoom(basicRtype.get(1), checkIn);

//        룸 테이블에서 해당 방이 예약불가인 날이 몇 개인지 개수세기 (날짜 between)
        int NumCity2 = roomRepository.countUnavailableRooms(checkIn, checkOut, basicRtype.get(0));
        int NumOcean2 = roomRepository.countUnavailableRooms(checkIn, checkOut, basicRtype.get(1));


//    1박일때
        if (count == 1) {

            if (NumCity == 0 && NumOcean == 0) {
                List<RoomDto> roomBasicDtoList = roomBasicRepository.selectLikeRtype(rtype);
                return roomBasicDtoList;

            } else if (NumCity == 1 && NumOcean == 1) {
                List<RoomDto> roomDtoList = roomRepository.selectLikeRoom(checkIn,rtype);
                return roomDtoList;

            } else if (NumCity == 0) {
                int yn = roomRepository.selectsearchRoom(checkIn, basicRtype.get(0));
                if (yn == 0) {
                    List<RoomDto> roomBasic = roomBasicRepository.selectRtypeList(basicRtype.get(0));
                    return roomBasic;
                } else {
                    List<RoomDto> room = roomRepository.selectLikeRoom(checkIn, basicRtype.get(0));
                    return room;
                }
            } else {
                int yn = roomRepository.selectsearchRoom(checkIn, basicRtype.get(1));
                if (yn == 0) {
                    List<RoomDto> roomBasic = roomBasicRepository.selectRtypeList(basicRtype.get(1));
                    return roomBasic;
                } else {
                    List<RoomDto> room = roomRepository.selectLikeRoom(checkIn, basicRtype.get(1));
                    return room;
                }
            }

        }
        //            연박일때
        else {
// 체크인 기간 중 예약 불가인 날이 없는 경우 : 룸 베이직 보여줌
            if (NumCity2 == 0 && NumOcean2 == 0) {
                List<RoomDto> roomBasicDtoList = roomBasicRepository.selectLikeRtype(rtype);
                return roomBasicDtoList;

            } else if (NumCity2 >= 1 && NumOcean2 >= 1) {
                List<RoomDto> roomDtoList = roomRepository.unavailableRooms(checkIn,checkOut,rtype );
                return roomDtoList;

            } else if (NumCity2 == 0) {
                int yn = roomRepository.selectsearchRoom(checkIn, basicRtype.get(0));
                if (yn == 0) {
                    List<RoomDto> roomBasic = roomBasicRepository.selectRtypeList(basicRtype.get(0));
                    return roomBasic;
                } else {
                    List<RoomDto> room = roomRepository.selectLikeRoom(checkIn, basicRtype.get(0));
                    return room;
                }
            } else {
                int yn = roomRepository.selectsearchRoom(checkIn, basicRtype.get(1));
                if (yn == 0) {
                    List<RoomDto> roomBasic = roomBasicRepository.selectRtypeList(basicRtype.get(1));
                    return roomBasic;
                } else {
                    List<RoomDto> room = roomRepository.selectLikeRoom(checkIn, basicRtype.get(1));
                    return room;
                }
            }
        }
    }


    //    TODO : 예약취소시 남은방 업데이트 함수
    public void plusRest(String checkIn, String rtype, String checkOut) {

        ArrayList<String> al = roomRepository.selectArray(checkIn, checkOut);
        for (int i = 0; i < al.size(); i++) {
//            REST 레코드 업데이트
                int room = roomRepository.selectPlusRest(al.get(i), rtype);
        }

    }


}
