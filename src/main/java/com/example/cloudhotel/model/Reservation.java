package com.example.cloudhotel.model;

import com.example.cloudhotel.controller.ReservationController;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.cloudhotel.model
 * fileName : Reservation
 * author : 605
 * date : 2023-06-21
 * description : 예약
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Entity
@Table(name="TB_RESERVATION")
@SequenceGenerator(
        name = "SQ_RESERVATION_GENERATOR"
        , sequenceName = "SQ_RESERVATION"
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
@Where(clause = "DELETE_YN = 'N'")
@SQLDelete(sql = "UPDATE TB_RESERVATION SET DELETE_YN = 'Y', RESERVATION_STATE = 'N', DELETE_TIME=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE RVNO = ?")


public class Reservation extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_RESERVATION_GENERATOR"
    )

    private Integer rvno;    // 예약번호

    private String membership;    // 멤버쉽 회원 유무

    private String username;    // 이름
    @Column
    private String email;   // 이메일
    @Column
    private String phone;   // 전화번호

    private String rtype;   // 룸이름

    private String pname;   // 패키지명

    private String checkIn;   // 예약시작날짜

    private String checkOut;   // 예약종료날짜

    private String reservationState; // 예약현황

    private String price;   // 결제가격




}
