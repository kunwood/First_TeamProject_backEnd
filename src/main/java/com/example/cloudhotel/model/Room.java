package com.example.cloudhotel.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.cloudhotel.model
 * fileName : Room
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
@Entity
@Table(name="TB_ROOM")
@SequenceGenerator(
        name = "SQ_ROOM_GENERATOR"
        , sequenceName = "SQ_ROOM"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
// soft delete
// @Where : (clause = "자동으로 추가할 조건절") : 자동으로 select 조건으로 추가함
@Where(clause = "DELETE_YN = 'N'")
// delete 실행 -> 대체해서 실행될 sql문 지정
// 사용법) @WQLDelete(sql = "대체 실행 sql문")
@SQLDelete(sql = "UPDATE TB_ROOM SET DELETE_YN = 'Y', DELETE_TIME=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE RNO = ?")


public class Room extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SQ_ROOM_GENERATOR")
    private Integer rno;
    private String rtype;
    private Integer rprice;
    private String checkIn;
    private Integer total;
    private Integer rest;
    private String people;
    private String checkOut;
    private String roomurl;


}
