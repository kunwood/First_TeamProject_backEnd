package com.example.cloudhotel.dto;

import lombok.*;

/**
 * packageName : com.example.cloudhotel.dto
 * fileName : RoomDto
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


public interface RoomDto {

//    룸 베이직
Integer getRno();
    String  getTypeCode();
    String  getRtype();
    Integer getRprice();
    String  getPeople();
    Integer getTotal();

//    룸
String  getCheckIn();
    String  getCheckOut();
    Integer getRest();
    String getRoomurl();





}
